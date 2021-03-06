package com.geargames.awtdemo.application;

import com.geargames.common.logging.Debug;
import com.geargames.common.Port;
import com.geargames.common.packer.PManager;
import com.geargames.common.util.ArrayByte;
import com.geargames.common.util.ArrayList;
import com.geargames.common.util.ArrayShortDual;
import com.geargames.common.Graphics;
import com.geargames.platform.packer.Image;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/*
* Класс предназначен для разбора данных из пакера, а именно:
* - загрузка данных
* - инициализация имиджей
* - инициализация данных
*
* На данный момент Пакер выгружает 3 файла
* - 'i0' - пакет с имиджами
* - 'd0' - пакет с данными
* - 'Graph.java' - набор констант к данным
*
*/
public class Loader {

    private final boolean DEBUG = false;

    private final static byte PARAM_COUNT = Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT + Graph.PARAM_INT_COUNT;
    //    public static short[][] arr_i = new short[PARAM_COUNT][Render__.PARAM_MAX_SIZE];
///*ObjC comment*/public short[][] arr_i = new short[PARAM_COUNT][];
    private ArrayShortDual dualArrayShort;
    /*ObjC uncomment*///public static short[][] arr_i = new short[PARAM_COUNT][PARAM_MAX_SIZE];

    private ArrayList images;// = new Image[Graph.IMG_COUNT];//хранилище имиджей [номер пакета][номер имиджа]
    private boolean isTexturesLoaded;

    private Manager manager;

    public Loader(Manager manager) {
        this.manager = manager;
        init_();
        /*ObjC uncomment*///return self;
    }

    private void init_() {
        setTexturesLoaded(false);
        images = new ArrayList(Graph.IMG_COUNT);
        for (int i = 0; i < Graph.IMG_COUNT; i++) images.add(new Image(null));
    }

    public void loadTextures(Graphics graphics) {//вызов загрузки текстур можно делать только из потока onCreated из GLRenderer
        //в графикс будем добавлять текстуры
        isTexturesLoaded = false;
        loadImagesPackets(graphics);
        isTexturesLoaded = true;
    }

    public void loadImagesPackets(Graphics graphics) {
        int pack_id = 0;
        for (int ip = 0; ip < 20; ip++) {
            int count = 0;
            for (int i = 0; i < 16; i++) {
                if (pack_id == Graph.getIMG_INDEX(i)) {
                    count++;
                }
            }
            if (count > 0) loadImages(graphics, pack_id, count);
            pack_id++;
        }
    }

    private void loadImages(Graphics graphics, int pack_cur, int size) {//инициализация имиджей из пакетов

        String pack_name = "/i" + pack_cur;
        if (Port.IS_DOUBLE_GRAPHIC/* || (Port.isFourThirdsGraphic() && game.Port.isAndroid())*/)
            pack_name = pack_name + "_x2";
        else if (Port.IS_FOURTHIRDS_GRAPHIC) pack_name = pack_name + "_x4_3";
        int img_count = size;//картинок в каждом пакете

        int img_square = 0;
        int len_b0;
        int data_len;
        ArrayByte data;

        Debug.debug("Load images from " + pack_name + "\tcount:" + img_count);
        try {

            DataInputStream dis = new DataInputStream(manager.getMidlet().getResourceAsStream(pack_name));

            for (int i = 0; i < img_count; i++) {
                int img_cur = pack_cur * img_count + i;

                len_b0 = dis.readByte() & 0xff;
                if (len_b0 == 0xff) break;

                int len_b1 = dis.readByte() & 0xff;
                int len_b2 = dis.readByte() & 0xff;
                data_len = len_b0 << 16 | len_b1 << 8 | len_b2;
                if (DEBUG) {
                    Debug.debug(img_cur + " " + data_len + " ");
                }

                data = new ArrayByte(data_len);
                dis.read(data.getArray(), 0, data_len);

                try {
                    if (!((Image) images.get(img_cur)).isCreated())
                        images.add(img_cur, Image.createImage(data.getArray(), 0, data_len));
                    /*ObjC uncomment*///if ([Port isOpenGL]) [gles_view addImageToTexture:images[img_cur]];
                    if (Port.OPEN_GL)
                        graphics.addTexture((Image) images.get(img_cur));//при ините текстуры битмап убьётся
                    Debug.debug("Memory, img:" + img_cur + "; MEM:" + Manager.getFreeMemory());
                    if (DEBUG/* && false*/) {
                        img_square += ((Image) images.get(img_cur)).getWidth() * ((Image) images.get(img_cur)).getHeight();
                    }

                } catch (Exception e) {
                    if (DEBUG) {
                        Debug.debug("Loading image error. img:" + img_cur + "\tfile:" + pack_cur + "\tdata_len:" + data_len);
                    }
                    Debug.error("", e);
                }
                data.free();
                System.gc();
            }

            dis.close();
            dis = null;//for ObjC - autorelsed
            System.gc();

        } catch (Exception ex) {
            if (DEBUG) {
                ex.printStackTrace();
            }
            Debug.error("File is not loaded, name:" + pack_name, ex);
        }
        if (DEBUG) {
            Debug.debug("Loaded :" + img_square + " pixels\tMemory x2:" + img_square * 2);
        }

    }

    public void loadPackerData() {//загрузка и размещение данных пакера

        try {

            String name = "/d0";
            DataInputStream dis = new DataInputStream(manager.getMidlet().getResourceAsStream(name));

            dualArrayShort = new ArrayShortDual(PARAM_COUNT);
            for (int i = 0; i < Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT + Graph.PARAM_INT_COUNT; i++) {
                int len = dis.readShort();
                dualArrayShort.createY(i, len);
                String str = "Load " + i + "\tlen:" + len + " ";
                if (i < Graph.PARAM_BYTE_COUNT) {//BYTE
                    ArrayByte arrayByte = new ArrayByte(len);
                    dis.read(arrayByte.getArray(), 0, len);
                    for (int a = 0; a < len; a++) {
                        dualArrayShort.set(i, a, arrayByte.get(a));
                        if (DEBUG) str += dualArrayShort.get(i, a) + ",";
                    }
                    arrayByte.free();
                } else if (i < Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT) {//SHORT
                    ArrayByte arrayByte = new ArrayByte(len * 2);
                    dis.read(arrayByte.getArray(), 0, len * 2);
                    for (int a = 0; a < len; a++) {
                        dualArrayShort.set(i, a, (short) ((arrayByte.get(a * 2) << 8) | (arrayByte.get(a * 2 + 1) & 0xff)));
                        if (DEBUG) str += dualArrayShort.get(i, a) + ",";
                    }
                    arrayByte.free();
                } else {//INT
                    ArrayByte arrayByte = new ArrayByte(len * 4);
                    dis.read(arrayByte.getArray(), 0, len * 4);
                    for (int a = 0; a < len; a++) {
                        dualArrayShort.set(i, a, (short) ((arrayByte.get(a * 4) << 24) | ((arrayByte.get(a * 4 + 1) & 0xff) << 16)
                                | ((arrayByte.get(a * 4 + 2) & 0xff) << 8) | (arrayByte.get(a * 4 + 3) & 0xff)));
                        if (DEBUG) str += dualArrayShort.get(i, a) + ",";
                    }
                    arrayByte.free();
                }
                if (DEBUG/* && false*/) {
                    Debug.debug(str);
                }
            }
            dis.close();
            dis = null;

            if (DEBUG) drawData();
            //convertData();

        } catch (Exception e) {
            Debug.error("", e);
        }

    }

    public void loadPackerData__() {//загрузка и размещение данных пакера
        //TODO НЕ УДАЛЯТЬ! пока не будет обёртки для ObjC
        try {

            String name = "/d0";
            //name = Port.RETINA ? name.concatC("2") : name;Данные общие для всех экранов айфона
            DataInputStream dis = new DataInputStream(Manager.getInstance().getMidlet().getResourceAsStream(name));

            short[][] arr_i = new short[PARAM_COUNT][];
            for (int i = 0; i < Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT + Graph.PARAM_INT_COUNT; i++) {
                int len = dis.readShort();
                /*ObjC comment*/
                arr_i[i] = new short[len];
                String str = "Load " + i + "\tlen:" + len + " ";
                if (i < Graph.PARAM_BYTE_COUNT) {//BYTE
                    /*ObjC comment*/
                    byte[] arr = new byte[len];
                    /*ObjC uncomment*///char *arr = (char *)malloc(sizeof(char) * (len));/*ObjC noconvert*/
                    dis.read(arr, 0, len);
                    for (int a = 0; a < len; a++) {
                        arr_i[i][a] = arr[a];
                        if (DEBUG) str += arr_i[i][a] + ",";
                    }
                    /*ObjC uncomment*///free(arr);
                } else if (i < Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT) {//SHORT
                    /*ObjC comment*/
                    byte[] arr = new byte[len * 2];
                    /*ObjC uncomment*///char *arr = (char *)malloc(sizeof(char) * (len * 2));/*ObjC noconvert*/
                    dis.read(arr, 0, len * 2);
                    for (int a = 0; a < len; a++) {
                        arr_i[i][a] = (short) ((arr[a * 2] << 8) | (arr[a * 2 + 1] & 0xff));
                        if (DEBUG) str += arr_i[i][a] + ",";
                    }
                    /*ObjC uncomment*///free(arr);
                } else {//INT
                    /*ObjC comment*/
                    byte[] arr = new byte[len * 4];
                    /*ObjC uncomment*///char *arr = (char *)malloc(sizeof(char) * (len * 4));/*ObjC noconvert*/
                    dis.read(arr, 0, len * 4);
                    for (int a = 0; a < len; a++) {
                        arr_i[i][a] = (short) ((arr[a * 4] << 24) | ((arr[a * 4 + 1] & 0xff) << 16) | ((arr[a * 4 + 2] & 0xff) << 8) | (arr[a * 4 + 3] & 0xff));
                        if (DEBUG) str += arr_i[i][a] + ",";
                    }
                    /*ObjC uncomment*///free(arr);
                }
                if (DEBUG/* && false*/) {
                    Debug.debug(str);
                }
            }
            dis.close();
            dis = null;

            //if (DEBUG) drawData();

        } catch (Exception e) {
            Debug.error("", e);
        }

    }

    public ArrayShortDual getData() {
        return dualArrayShort;
    }

    public Image getImage(int img_num) {
        if (images == null) return null;
        return (Image) images.get(img_num);
    }

    public void drawData() {
        String str = "";
        for (int i = 0; i < Graph.PARAM_BYTE_COUNT + Graph.PARAM_SHORT_COUNT; i++) {
            for (int x = 0; x < 20/* && x < arr_i[i].length*/; x++) {
                if (dualArrayShort.length(i) <= x) continue;
                try {
                    if (i < Graph.PARAM_BYTE_COUNT) {//BYTE
                        str += dualArrayShort.get(i, x) + ",";
                    } else {//SHORT
                        str += dualArrayShort.get(i, x) + ",";
                    }
                } catch (Exception e) {
                    Debug.error("Loader.drawData error.", e);
                }
            }
            str += "\n";
        }
        Debug.debug(str);
    }

    //загрузка текстов из файла
    private Vector text_list;

    private Vector getText(String path) {
        StringBuffer strBuff = new StringBuffer();
        text_list = new Vector();
        text_list.addElement("");//первая строка пустая для совпадения нумерации с текстовым файлом
        try {
            DataInputStream dis = new DataInputStream(Manager.getInstance().getMidlet().getResourceAsStream(path));
            int ch;
            while ((ch = dis.read()) != -1) {
                if (ch == 13) {//\t  выделяем конец строки
                    ch = dis.read();
                    if (ch != 10) throw new Exception("Text error, new line corrupt.");//\n
                    text_list.addElement(strBuff.toString());
                    strBuff = null;
                    strBuff = new StringBuffer();
                } else {
                    strBuff.append((char) ((ch >= 0xc0 && ch <= 0xFF) ? (ch + 0x350) : ch));
                }
            }
            dis.close();
        } catch (Exception e) {
            Debug.error("", e);
        }
        return text_list;
    }

    public String getText(int ind) {
        return (String) text_list.elementAt(ind);
    }

    public void setTexturesLoaded(boolean texturesLoaded) {
        isTexturesLoaded = texturesLoaded;
    }

    public boolean isTexturesLoaded() {
        return isTexturesLoaded;
    }

    public void loadPacker(Graphics g, PManager packer) {
        String name = "/d0";
        try {
            InputStream is = Manager.getInstance().getMidlet().getResourceAsStream(name);
            packer.loadData(is);
            is.close();

            String pack_name = "/i0";
            if (Port.IS_DOUBLE_GRAPHIC) pack_name += "_x2";
            else if (Port.IS_FOURTHIRDS_GRAPHIC) pack_name += "_x4_3";
            is = manager.getMidlet().getResourceAsStream(pack_name);
            packer.loadImages(g, is);
            is.close();

        } catch (IOException e) {
            Debug.error("", e);
        }
    }
}
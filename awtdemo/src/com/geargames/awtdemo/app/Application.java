package com.geargames.awtdemo.app;

import com.geargames.Debug;
import com.geargames.Recorder;
import com.geargames.awt.Anchors;
import com.geargames.awt.TextHint;
import com.geargames.awtdemo.packer.PUnitCreator;
import com.geargames.awt.timers.TimerManager;
import com.geargames.common.String;
import com.geargames.common.packer.PFont;
import com.geargames.common.packer.PFontManager;
import com.geargames.common.util.ArrayByte;
import com.geargames.common.util.ArrayIntegerDual;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.packer.Graphics;
import com.geargames.packer.Image;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class Application extends com.geargames.awt.Application {

    //todo: Удалить mult_fps
    public static final int mult_fps = /*@MULT_FPS@*/4/*END*/;//1 2 4 = 6 12 24

    //    private static Lock startLock = new MELock();
    private PPanelManager panels = PPanelManager.getInstance();

   // Состояние регистрации
    private Loader loader;
    private Render render;
    private PFontManager fontManager;

    private boolean vibrationEnabled;
    private boolean soundEnabled;

    private int userId, userMidletId;
    private int clientId;

    private boolean isLoading = true; // true, если данные загружаются
    private static int globalTick;

    protected Graphics graphicsBuffer;
    protected Image i_buf;
    private boolean is_drawing;

    //TODO Переименовать stateInfo
    private String stateInfoString = null;
    private Point stateInfoPosition = new Point(12, 36);
    private byte stateInfoAnchors = Anchors.TOP_LEFT_ANCHOR;
    // splash
    private Image backgroundImage = createBackgroundImage();

    // Конструктор
    public Application() {
        super();
        if (graphicsBuffer == null) {
            createScreenBuffer(Port.getW(), Port.getH());
        }
//        drawSplash();
        /*ObjC uncomment*///return self;
    }

    public static Application getInstance() {
        Application instance = (Application)com.geargames.awt.Application.getInstance();
        if (instance == null) {
//            startLock.lock();
            instance = new Application();
//            startLock.release();
        }
        return instance;
    }

    public Graphics getGraphics() {
        return graphicsBuffer;
    }

    public Render getRender() {
        return render;
    }

    public void createScreenBuffer(int w, int h) {
        try {
            i_buf = Image.createImage(w, h);
            graphicsBuffer = i_buf.getGraphics();
            if (render != null) {
                getGraphics().setRender(render);
            }
        } catch (Exception ex) {
            Debug.logEx(ex);
        }
    }

    private void drawSplash() {
        try {
            if (backgroundImage != null) {
//                backgroundImage.draw(graphicsBuffer, 0, 0);
                graphicsBuffer.drawImage(backgroundImage, i_buf.getWidth() / 2, i_buf.getHeight() / 2,
                        Graphics.HCENTER | Graphics.VCENTER);
            } else {
                graphicsBuffer.setColor(0xffffff);
                graphicsBuffer.fillRect(0, 0, i_buf.getWidth(), i_buf.getHeight());
            }

            if (stateInfoString != null && stateInfoString.length() > 0) {
                graphicsBuffer.setColor(0x000000);
                //TODO установить фонт!
                graphicsBuffer.drawString(stateInfoString, 12, Port.getH() - 36, 0);
            }

            Manager.getInstance().repaintStart();
            Thread.yield();
            Manager.paused(10);
        } catch (Exception e) {
            logEx(e);
        }
    }

    public void drawSplash(java.lang.String str) {
        stateInfoString = String.valueOfC(str);
        drawSplash();
    }

    public void loading() {

        tSleep = System.currentTimeMillis();

        Debug.log(String.valueOfC("Memory total, free: ").
                concatL(/*Debug.formatSize*/(Manager.getTotalMemory())).
                concatC(", ").concatL(/*Debug.formatSize*/(Manager.getFreeMemory())));

        drawSplash("Loading...");

        loader = new Loader(Manager.getInstance());
        render = new Render();

        render.setCreator(new PUnitCreator());
        render.create();
        loader.loadPacker(graphicsBuffer, render);
        getGraphics().setRender(render);

        fontManager = new PFontManager();

        ArrayIntegerDual fontIndexes = new ArrayIntegerDual(1, 2);

        fontIndexes.set(0, 0, Graph.SPR_FONT_SYMB);
        fontIndexes.set(0, 1, Graph.SPR_FONT_RU);

        fontManager.init(render, fontIndexes);

        PFont baseFont = fontManager.getFont(0);
        PFontCollection.initiate(fontManager, baseFont);

//        drawSplash(String.valueOfC("Init network..."));

        initPreferenceOnStart();
        isLoading = false;

        panels.initiate(render);

        TextHint textHint = TextHint.getInstance();
        textHint.setSkinObject(render.getFrame(675), render, 16, 24, 16, 24); //todo: Установить правильный скин и размеры
//        textHint.setDefaultFont(PFontCollection.getFontHint());


        stateInfoString = String.valueOfC("");
    }

    public void resetPreference() {
        vibrationEnabled = true;
        soundEnabled = true;
    }

    private void initPreferenceOnStart() {
        if (!loadOptionsRMS()) {
            resetPreference();
            saveOptionsRMS();//при запуске создаём рмс
        }
    }

    protected void onStop(boolean correct) {
        log(String.valueOfC("onStop.disconnect"));
        if (correct)
            saveOptionsRMS(); // При выходе из игры
        Debug.trace("Application.onStop");
    }

    public void destroy(boolean correct) {
        log(String.valueOfC("destroy ").concatC(correct ? "correct" : "uncorrect"));
        Manager.getInstance().destroy(correct);
    }

    private final java.lang.String RMS_SETTINGS = "awtdemo";

    public boolean saveOptionsRMS() {
        boolean res = false;
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeByte(1);
            dos.writeBoolean(vibrationEnabled);
            dos.writeBoolean(soundEnabled);
            dos.writeInt(userId);
            dos.writeInt(clientId);
            dos.writeInt(userMidletId);

            res = Recorder.RMSStoreSave(RMS_SETTINGS, baos, false);

            Debug.log(String.valueOfC("Rms.Prefs saved: vibra:").concatC(vibrationEnabled ? "On" : "Off").concatC(" sound:").concatC(soundEnabled ? "On" : "Off").concatC(" userId:").concatI(userId).concatC(" clientId:").concatI(clientId));
        } catch (Exception e) {
            Debug.trace(String.valueOfC("Save prefs "), e);
            res = false;
        } finally {
            try {
                if (dos != null) dos.close();
                if (baos != null) baos.close();
            } catch (Exception e) {
                logEx(e);
            }
            return res;
        }
    }

    public boolean loadOptionsRMS() {
        boolean res = false;

        try {
            ArrayByte bData = Recorder.RMSStoreRead(RMS_SETTINGS, false);
            if (bData != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(bData.getArray());
                DataInputStream dis = new DataInputStream(bais);
                try {
                    byte version = dis.readByte();
                    if (version == 1) {
                        vibrationEnabled = dis.readBoolean();
                        soundEnabled = dis.readBoolean();
                        userId = dis.readInt();
                        clientId = dis.readInt();
                        userMidletId = dis.readInt();
                        res = true;

                        Debug.log(String.valueOfC("Rms.Prefs load:"));
                        Debug.log(String.valueOfC(" id:").concatI(userId));
                    }
                } catch (Exception e) {
                    Debug.trace(String.valueOfC("RMSLoad prefs "), e);
                    Recorder.RMSStoreClean(RMS_SETTINGS);
                    return false;
                }
                if (dis != null) dis.close();
                /*ObjC uncomment*///[bais release];
                /*ObjC uncomment*///[dis release];
            }
            return res;
        } catch (Exception e) {
            Debug.trace(String.valueOfC("RMSLoad stream "), e);
            return false;
        }
    }

    public PFontManager getFontManager() {
        return fontManager;
    }

    protected void eventProcess() {
        super.eventProcess();
//        if (Application.isTimer(Manager.TIMERID_KEYDELAY) && !Application.isTimer(Manager.TIMERID_KEYREPEAT))//TODO сделать один интервал на все фпс
//            eventAdd(Event.EVENT_KEY_REPEATED, Manager.getInstance().getPressedKey(), null);
    }

    // ---------------MAIN LOOP------------------

    public void mainLoop() {
        try {
            if (Manager.getInstance().isSuspended() || isLoading) {
                Manager.paused(10);
                return;
            }
            TimerManager.update();
            Ticker.processTickers();
            eventProcess();
            gameEvent(Event.EVENT_TICK, 0, 0, 0); //todo: убрать тики

            draw(graphicsBuffer);

            if (true/* || this.equals(manager.getDisplay())*/) {
                is_drawing = true;
                /*ObjC uncomment*///is_drawing = false;
                Manager.getInstance().repaintStart();
                Thread.yield();
                while (is_drawing) {
                    Thread.yield();
                    Manager.paused(5);
                }
            }
            int fps = 8 * mult_fps;
            manageFPS(fps);
            globalTick++;
        } catch (Exception e) {
            logEx(e);
        }
    }

    private int arr_tick = 0;
    private boolean arr_side = true;
    private long tSleep;
    private long fps_cur;

    public void manageFPS(int fps) {
        if (fps != 0) {
            int timeFPS = (1000 / fps);//задержка для установленного фпс
            long timeElapsed = System.currentTimeMillis() - tSleep;//реальная задержка
            long paused = timeFPS - timeElapsed;//делаем затержку для выдерживания фпс
            if (timeElapsed <= 0) timeElapsed = 1;
            if (paused > 0) {
                Manager.paused(paused);
            }
            fps_cur = (fps_cur + 1000 / (timeElapsed/* - (paused > 0 ? paused : 0)*/)) / 2;
            tSleep = System.currentTimeMillis();
        } else {
            Manager.paused(1);
        }

        if (arr_side) {
            arr_tick++;
        } else {
            arr_tick--;
        }
        if (arr_tick > 2) {
            arr_side = false;
        } else if (arr_tick == 0) {
            arr_side = true;
        }
    }

//    public static boolean isTick() {
//        return (globalTick % mult_fps == 0) ? true : false;
//    }
//
//    public static int getTick() {
//        return globalTick;
//    }

    // --------------- MESSAGE HANDLERS --------------------------------------------------------------------------------

    /**
     * Выполнение всех манипуляций на один игровой тик
     */
    protected void onEvent(com.geargames.common.Event event) {
//        Object element = event.getData();
//        if (element != null) {
//            ((AWTObject)element).event(event.getUid(), event.getParam(), event.getX(), event.getY());
//        } else {
            gameEvent(event.getUid(), event.getParam(), event.getX(), event.getY());
//        }
    }

    /**
     * Выполнение всех манипуляций на один игровой тик
     */
    public void gameEvent(int code, int param, int x, int y) {
        panels.event(code, param, x, y);
    }

    // ----------------- DRAWING ---------------------------------------------------------------------------------------

    private void draw(Graphics graphics) {
        try {
            if (backgroundImage != null) {
//                backgroundImage.draw(graphics, 0, 0);
                graphics.drawImage(backgroundImage, i_buf.getWidth() / 2, i_buf.getHeight() / 2,
                        Graphics.HCENTER | Graphics.VCENTER);
            } else {
//                graphics.setColor(0xffffff);
//                graphics.fillRect(0, 0, i_buf.getWidth(), i_buf.getHeight());
            }

//            graphics.setColor(0x000000);
            graphics.setColor(0xffffff);
            panels.draw(graphics);

            if (stateInfoString != null && stateInfoString.length() > 0) {
                graphics.setColor(0xffffff);
                //TODO установить фонт!
                graphics.drawString(stateInfoString, 12, Port.getH() - 36, 0);
            }
        } catch (Exception e) {
            logEx(e);
        }
        /*ObjC uncomment*///if ([Port isOpenGL]) [gles_view paintEnd];
    }

    public Image getBuffer() {
        return i_buf;
    }

    public boolean isDrawing() {
        return is_drawing;
    }

    public void setIsDrawing(boolean is_drawing_) {
        this.is_drawing = is_drawing_;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private void log(String str) {
        Debug.log(str);
    }

    private void logEx(Exception e) {
        Debug.logEx(e);
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected Image createBackgroundImage()
    {
        try {
            return Image.createImage(String.valueOfC("/s1.png"), Manager.getInstance());
        } catch (Exception ex) {
            Debug.logEx(ex);
            return null;
        }
    }

    public Image getBackgroundImage()
    {
        return backgroundImage;
    }

    public void setBackgroundImage(Image image)
    {
        backgroundImage = image;
    }
}

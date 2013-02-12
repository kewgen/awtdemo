package com.geargames.awtdemo.app;

import com.geargames.Debug;
import com.geargames.Recorder;
import com.geargames.awt.Anchors;
import com.geargames.awtdemo.packer.PUnitCreator;
import com.geargames.common.String;
import com.geargames.common.packer.PFont;
import com.geargames.common.packer.PFontComposite;
import com.geargames.common.packer.PFontManager;
import com.geargames.common.util.ArrayByte;
import com.geargames.common.util.ArrayIntegerDual;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.packer.Graphics;
import com.geargames.packer.Image;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Vector;

public final class Application {

    // Posible names: appInstance
    private static Application instance;

    public static final int mult_fps = /*@MULT_FPS@*/2/*END*/;//1 2 4 = 6 12 24

    // Состояние регистрации
    private Loader loader;
    private PFontManager fontManager;

    private boolean vibrationEnabled;
    private boolean soundEnabled;

    private int userId, userMidletId;
    private int clientId;

    private boolean isLoading = true; // true, если данные загружаются
    private Render render;
    private static int globalTick;

//    private static Lock startLock = new MELock();
    private PPanelManager panels = PPanelManager.getInstance();

    //TODO Переименовать stateInfo
    private String stateInfoString = null;
    private Point stateInfoPosition = new Point(12, 36);
    private byte stateInfoAnchors = Anchors.TOP_LEFT_ANCHOR;
    // splash
    private Image backgroundImage = createBackgroundImage();

    private PFont font10;
    private PFont baseFont;

    public PFont getFont10() {
        return font10;
    }

    public PFont getBaseFont() {
        return baseFont;
    }

    public Render getRender() {
        return render;
    }

    public Graphics getGraphics() {
        return graphicsBuffer;
    }

    // Конструктор
    private Application() {
        if (graphicsBuffer == null) {
            createScreenBuffer(Port.getW(), Port.getH());
        }
//        drawSplash();
        /*ObjC uncomment*///return self;
    }

    public static Application getInstance(){
        if(instance == null){
//            startLock.lock();
            instance = new Application();
//            startLock.release();
        }
        return instance;
    }

//    public void drawSplash(String str) {
//        loadLog = str.toString();
//        drawSplash();
//    }
//
//    private void drawSplash() {
//        try {
//            if (splash == null) {
//                splash = Image.createImage(String.valueOfC("/s1.png"), Manager.getInstance());
//            }
//            graphicsBuffer.drawImage(splash, splash.getWidth() / 2, splash.getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
//            graphicsBuffer.setColor(0);
//            //todo установить фонт!
//            graphicsBuffer.drawString(String.valueOfC(loadLog), 5, Port.getH() - 20, 0);
//            Manager.getInstance().repaintStart();
//            Thread.yield();
//            Manager.paused(10);
//        } catch (Exception ex) {
//            Debug.logEx(ex);
//        }
//    }

    public void createScreenBuffer(int w, int h) {
        try {
            i_buf = Image.createImage(w, h);
            graphicsBuffer = i_buf.getGraphics();
            if (render != null) getGraphics().setRender(render);
        } catch (Exception ex) {
            Debug.logEx(ex);
        }
    }

    public void loading() {

        tSleep = System.currentTimeMillis();

        Debug.log(String.valueOfC("Memory total,free:").concatL(Manager.getTotalMemory()).concatC(",").concatL(Manager.getFreeMemory()));

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


        baseFont = fontManager.getFont(0);
        font10 = fontManager.createReSizedFont((PFontComposite) baseFont, 10);

//        drawSplash(String.valueOfC("Init network..."));

        initPreferenceOnStart();
        isLoading = false;

//        rmenuRun();

        panels.initiate(render);
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
        if (correct) saveOptionsRMS();//при выходе из игры
        Debug.trace("Application.onStop");
    }

    public void destroy(boolean correct) {
        log(String.valueOfC("destroy ").concatC(correct ? "correct" : "uncorrect"));
        Manager.getInstance().destroy(correct);
    }

    private final java.lang.String RMS_SETTINGS = "pfp";

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

    // ------------ EVENTS CONTROL -------------
    // Очередь сообщений, дублёр предназначен для исключения добавления нового события в момент обработки списка событий
    private Vector msgQueue;// = new Vector(64);

    public void eventAdd(int eventid, int param, Object data) {
        eventAdd(eventid, param, data, 0, 0);
    }

    public void eventAdd(int eventid, int param, Object data, int x, int y) {
        if (msgQueue == null) {
            msgQueue = new Vector(64);
        }
        boolean normal = msgQueue.size() < 64;
        if (!normal) Debug.warning(String.valueOfC("queue length exceed 64 events"));
        Event event = new Event(eventid, param, data, x, y);
        msgQueue.addElement(event);
    }

    protected void eventProcess() {
        if (msgQueue == null) return;

        try {
            //передача указателей на хранилища событий  для разнесения обработки и добавления событий
            synchronized (msgQueue) {

                while (!msgQueue.isEmpty()) {
                    Event event = (Event) msgQueue.firstElement();
                    msgQueue.removeElement(event);//перед вызовом события нужно его убрать из очереди на случай эксепта
                    onEvent(event);
                    event = null;//ObjC
                }

            }
            if (Application.isTimer(Manager.TIMERID_KEYDELAY) && !Application.isTimer(Manager.TIMERID_KEYREPEAT))//TODO сделать один интервал на все фпс
                eventAdd(Event.EVENT_KEY_REPEATED, Manager.getInstance().getPressedKey(), null);
        } catch (Exception e) {
            Debug.logEx(e);
        }
    }


    // ------------ TIMERS CONTROL -------------
    private static Vector timers;

    //Timers
    public final static int TIMER_CONTROLPACKET = 0x000001FF;//отправка контрольного пакета
    public final static int TIMER_CONTROLPACKET_MS = 30 * 1000;

    public static boolean isTimer(int timerId) {
        return Application.findTimer(timerId) != null;
    }

    private static Etimer findTimer(int timerId) {
        if (timers == null) timers = new Vector(16);
        for (Enumeration e = timers.elements(); e.hasMoreElements(); ) {
            Etimer timer = (Etimer) e.nextElement();
            if (timer.getId() == timerId)
                return timer;
        }
        return null;
    }

    public final void setTimer(int timerId, long interval) {
        Application.setTimer(timerId, interval, 0, false);
    }

    public static final void setTimer(int timerId, long interval, long data, boolean periodic) {

        Etimer timer = Application.findTimer(timerId);

        if (timer == null) {
            timer = new Etimer(timerId, interval, data, periodic);
            timers.addElement(timer);
        }
        //Debug.trace("timer " + Integer.toHexString((int) timer[0]) + " set");
    }

    public final void resetTimer(int timerId) {
        Etimer timer = Application.findTimer(timerId);
        if (timer != null) timer.setTime(System.currentTimeMillis());
    }

    public final void killTimer(int timerId) {
        for (Enumeration e = timers.elements(); e.hasMoreElements(); ) {
            Etimer timer = (Etimer) e.nextElement();
            if (timer.getId() == timerId) {
                timers.removeElement(timer);
                return;
            }
        }
    }

    public final long getTimerElapsedTime(int timerId) {
        Etimer timer = Application.findTimer(timerId);
        //Debug.assertMsg("getTimerElapsedTime. Timer " + timerId + " not found", timer != null);
        return System.currentTimeMillis() - timer.getTime();
    }

    public final boolean isTimerExpired(int timerId) {
        Etimer timer = Application.findTimer(timerId);
        //Debug.assertMsg("isTimerExpied. Timer " + timerId + " not found", timer != null);
        long timedelta = (System.currentTimeMillis() - timer.getTime());
        return timedelta >= timer.getWait();
    }

    public final void clearTimers() {
        timers.removeAllElements();
    }

    public void processTimers() {
        if (timers == null) return;
        for (Enumeration e = timers.elements(); e.hasMoreElements(); ) {
            Etimer timer = (Etimer) e.nextElement();

            long timedelta = (System.currentTimeMillis() - timer.getTime());
            if (timedelta >= timer.getWait()) {
                int timer_ = timer.getId();
                eventAdd(Event.EVENT_TIMER_END, timer_, timer);
                if ((timer.getData() & 0x8000000000000000L) != 0) {
                    timer.setTime(System.currentTimeMillis());
                } else {
                    timers.removeElement(timer);
                }
            }
        }
    }


    // ---------------MAIN LOOP------------------

    protected Graphics graphicsBuffer;
    protected Image i_buf;
    private boolean is_drawing;
    private int time_delay_ai;
    private int time_delay_render;

    public void mainLoop() {

        try {

            if (Manager.getInstance().isSuspended() || isLoading) {
                Manager.paused(10);
                return;
            }
            long time_delay_ai_start = System.currentTimeMillis();
            processTimers();
            Ticker.processTickers();
            eventProcess();
//            if (getApplicationState() == S_MAP)
                gameEvent(Event.EVENT_TICK, 0, 0, 0);
            time_delay_ai = (int) (System.currentTimeMillis() - time_delay_ai_start);

            long time_delay_render_start = System.currentTimeMillis();
            draw(graphicsBuffer);
            time_delay_render = (int) (System.currentTimeMillis() - time_delay_render_start);

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

        if (arr_side) arr_tick++;
        else arr_tick--;
        if (arr_tick > 2) arr_side = false;
        else if (arr_tick == 0) arr_side = true;
    }

    public static boolean isTick() {
        return (globalTick % mult_fps == 0) ? true : false;
    }

    public static int getTick() {
        return globalTick;
    }

    /**
     * Выполнение всех манипуляций на один игровой тик
     */
    public void gameEvent(int code, int param, int x, int y) {
        panels.event(code, param, x, y);
//        if (battleMap != null) {
//            battleMap.event(code, param, x, y);
//        }
//        switch (code) {
//            case Event.EVENT_KEY_UP:
//            case Event.EVENT_KEY_DOWN:
//                break;
//            case Event.EVENT_KEY_PRESSED:
//            case Event.EVENT_KEY_REPEATED:
//                break;
//            case Event.EVENT_TOUCH_RELEASED:
//                break;
//        }
    }

    // ----------------- DRAWING ---------------------------------------------------------------------------------------

    private void draw(Graphics graphics) {
        try {
//          if (splash == null)
//              splash = Image.createImage(String.valueOfC("/s1.png"), Manager.getInstance());
//          graphics.drawImage(splash, splash.getWidth() / 2, splash.getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);

            if (backgroundImage != null)
                graphics.drawImage(backgroundImage, backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2,
                        Graphics.HCENTER | Graphics.VCENTER);

            graphics.setColor(0xffffff);
            graphics.fillRect(0, 0, i_buf.getWidth(), i_buf.getHeight());

            graphics.setColor(0x000000);
            panels.draw(graphics);

            if (stateInfoString != null && stateInfoString.length() > 0) {
                graphics.setColor(0x000000);
                //TODO установить фонт!
                graphicsBuffer.drawString(stateInfoString, 12, Port.getH() - 36, 0);
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


    // --------------- MESSAGE HANDLERS --------------------------------------------------------------------------------

    protected void onEvent(Event event) {
        int code = event.getUid();
        int param = event.getParam();

        if (code == Event.EVENT_TIMER_END) {
            switch (param) {
                case TIMER_CONTROLPACKET:
                    return;
            }
        }

        panels.event(code, param, event.getX(), event.getY());
    }

    // ------------------- MENU ----------------------------------------------------------------------------------------

//    private void rmenuRun() {
//        Manager.getInstance().setLSK(Manager.SK_OK);
//        Manager.getInstance().setRSK(Manager.SK_EXIT);
//    }
//
//    private void rmenuEventOn(int code, int param, int x, int y) {
//        //Debug.trace("Application.rmenuEventOn, " + code + "," + param);
//        switch (code) {
//            case Event.EVENT_KEY_PRESSED:
//            case Event.EVENT_KEY_REPEATED:
//                if (param == Canvas.KEY_OK || param == Canvas.KEY_FIRE) {
////                    mapRun();
//                    return;
//                } else if (param == Canvas.KEY_LEFT) {
////                    menu_x -= 5;
//                    return;
//                } else if (param == Canvas.KEY_RIGHT) {
////                    menu_x += 5;
//                    return;
//                } else if (param == Canvas.KEY_UP) {
////                    menu_y -= 5;
//                    return;
//                } else if (param == Canvas.KEY_DOWN) {
////                    menu_y += 5;
//                    return;
//                } else if (param == Canvas.KEY_CANCEL) {
//                    return;
//                }
//                break;
//            case Event.EVENT_TOUCH_RELEASED:
////                if (x < 40 && y < 40 || true) mapRun();
//                break;
//        }
//    }

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
        return null;
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

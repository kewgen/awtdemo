package com.geargames.awtdemo.app;

import com.geargames.Debug;
import com.geargames.MIDlet;
import com.geargames.Recorder;
import com.geargames.common.String;
import com.geargames.packer.Canvas;
import com.geargames.packer.Display;
import com.geargames.common.Graphics;
import com.geargames.awtdemo.awt.components.PPanelManager;

public final class Manager extends com.geargames.Manager implements Runnable {

    public final static boolean DEBUG = true;//главный дебаг, если false отключает все остальные дебаги

    // ****************************** Settings ******************************

    private Application app;
    private MIDlet midlet;
    private volatile boolean running;
    private boolean isSuspended;//поток приостановлен
    private Thread thread;

    private byte okLabelN;
    private byte cancelLabelN;
    private boolean enabledOkKey = true;
    private boolean enabledCancelKey = true;

    // ------------------ Manage ------------------------

    private static Manager self_manger;
    private Canvas canvas;

    private Manager(MIDlet midlet_) {
        initCanvas(midlet_);
        self_manger = this;
        midlet = midlet_;
        isSuspended = false;
        /*ObjC uncomment*///return self;
    }

    public static Manager getInstance(MIDlet midlet_) {
        if (self_manger == null) self_manger = new Manager(midlet_);
        return self_manger;
    }

    public static Manager getInstance() {
        return self_manger;
    }

    public static Manager getManager() {//для андроида после выхода из спячки
        return self_manger;
    }


    public void create() {
        isSuspended = false;

        canvas.detectKeys();
        canvas.setFullScreenMode(true);
        Recorder.setContext(midlet);
        app = Application.getInstance();
        loading();
    }

    public void loading() {
        app.loading();
    }


    // ------------------ Screen ------------------------
    public void paint(Graphics g) {
        //Manager.log("Manager.paint");
        if (Port.IS_CONSOLE) g.onCache(5000);
        try {
            if (app == null) return;//Application еще не создан
            g.drawImage(app.getBuffer(), Port.SCREEN_DX, Port.SCREEN_DY, 0);
        } catch (Exception e) {
            Manager.logEx(e);
        }
        //if (Port.OPEN_GL && Port.isAndroid()) game.app.getGraphics().finish();
        app.setIsDrawing(false);
        //Debug.trace(" paint");
    }


    public void repaintStart() {
        canvas.repaintStart();
        canvas.repaint();
        midlet.repaint();
    }

    public void resizeScreenBuffer(int w, int h) {//изменение размеров экранного буфера
        if (app == null) return;
        Port.setWH(w, h);
        app.createScreenBuffer(w, h);
        PPanelManager.getInstance().onScreenResize();
    }


    // ------------MIDLET EVENTS HANDLERS--------
    public void startApp() {// Обработчик события startApp мидлета
        isSuspended = false;
        create();
        if (Port.OPEN_GL && Port.IS_ANDROID) {//вызов потока аи и рендера делает OpenGL
            run_();
            //onResume();//оживляем GLSurface
        } else {
            startMainThread();
        }
    }

    public void pauseApp() {// Обработчик события pauseApp мидлета
        isSuspended = true;
        //onPause();
        Display.getDisplay(midlet).setCurrent(null);
    }

    @Override
    public void onStop() {
        destroy(true);
    }

    private boolean destroy_correct;// Обработчик события destroyApp мидлета

    public void destroy(boolean correct) {
        destroy_correct = correct;
        stopMainThread();
        //if (game.Port.isAndroid()) midlet.onDestroy();
        if (Port.OPEN_GL && Port.IS_ANDROID) runStop();
        midlet.notifyDestroyed();
        //setCanvasDisplay(null);
    }


    // ------------THREAD CONTROL----------------
    public void startMainThread() {// Запускает поток Main game.Game loop
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stopMainThread() {// Останавливает поток Main game.Game loop
        running = false;
        thread = null;
        Debug.log(String.valueOfC("Manager.stopMainThread"));
    }

    public void run_() {
        /*ObjC uncomment*///NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
        Debug.log(String.valueOfC("Manager.run_"));
        running = true;
        runStart();
        /*ObjC uncomment*///[pool release];
    }

    public void run() {
        Debug.log(String.valueOfC("Manager.run - Main thread running"));
        running = true;
        try {
            runStart();
            while (getRunning()) {
                mainLoop();
            }
            runStop();
        } catch (Exception e) {
            Debug.logEx(e);
            stopMainThread();
            Debug.log(String.valueOfC("Application.run").concatC(e.toString()));
        }
    }

    public boolean getRunning() {
        return running;
    }

    public void loadTextures() {//OpenGL загрузка текстур
    }

    public void mainLoop() {//Вызов основного игрового цикла. Он должен вызывать рендер нужных фреймов
        app.mainLoop();
    }

    protected final void runStart() {
        try {
            isSuspended = false;
            Display.getDisplay(midlet).setCurrent(this);
        } catch (Exception e) {
            e.printStackTrace();
            Debug.assertMsg(String.valueOfC("onStart error [FILELINE]"), false);
        }
    }

    protected final void runStop() {
        try {
            Debug.log(String.valueOfC("runStop"));
            app.onStop(true);
            Debug.close();
            //midlet.notifyDestroyed();
        } catch (Exception e) {
            e.printStackTrace();
            Debug.assertMsg(String.valueOfC("Error during stop [FILELINE]"), false);
        }
    }


    // ------------KEYS CONTROL----------------

    private int lastKey = 0;
    private int pressedKey = 0;
    public final static int KEYDELAY = 10 * 1000;
    public final static int TIMERID_KEYDELAY = 0xDEDAAE77;
    public final static int KEYREPEAT = 200;
    public final static int TIMERID_KEYREPEAT = 0xEBEA888;

    final public static byte SK_OK = 0;
    final public static byte SK_EXIT = 3;
    final public static byte SK_NONE = 7;


    public final void setLSK(byte label) {
        okLabelN = label;
        enabledOkKey = okLabelN != SK_NONE;
    }

    public final boolean isLSKEnabled() {
        return enabledOkKey;
    }

    public final byte getLSK() {
        return okLabelN;
    }

    public final void setRSK(byte label) {
        cancelLabelN = label;
        enabledCancelKey = cancelLabelN != SK_NONE;
    }

    public final boolean isRSKEnabled() {
        return enabledCancelKey;
    }

    public final byte getRSK() {
        return cancelLabelN;
    }

    public int getPressedKey() {
        return pressedKey;
    }

    public void keyPressed(int key) {
        switch (key) {
            case Event.EVENT_KEY_UP:
                app.eventAdd(Event.EVENT_KEY_UP, 0, null);
                break;
            case Event.EVENT_KEY_DOWN:
                app.eventAdd(Event.EVENT_KEY_DOWN, 0, null);
                break;
        }
    }

    protected void keyRepeated(int key) {
    }

    protected void keyReleased(int key) {
        if (!enabledOkKey && key == Canvas.KEY_OK || !enabledCancelKey && key == Canvas.KEY_CANCEL)
            return;
        try {
            if (canvas.isKeyValid(key) || canvas.isTouchSupport) {
                app.eventAdd(Event.EVENT_KEY_RELEASED, key, null);
                lastKey = pressedKey = 0;
                app.killTimer(TIMERID_KEYREPEAT);
                app.killTimer(TIMERID_KEYDELAY);
            }
        } catch (Exception e) {
            Debug.assertMsg(String.valueOfC("keyReleased [FILELINE]"), false);
        }
    }

    public boolean onTouchEvent(int action, int x, int y) {
        x -= Port.SCREEN_DX;
        y -= Port.SCREEN_DY;
        switch (action) {
            case Canvas.ACTION_DOWN:
                app.eventAdd(Event.EVENT_TOUCH_PRESSED, 0, null, x, y);
                touch_down_last = System.currentTimeMillis();
                break;
            case Canvas.ACTION_UP:
                if (isDoubleClick()) {
                    app.eventAdd(Event.EVENT_TOUCH_DOUBBLE_CLICK, 0, null, x, y);
                }
                if (isLongClick()) {
                    app.eventAdd(Event.EVENT_TOUCH_LONG_CLICK, 0, null, x, y);
                }
                app.eventAdd(Event.EVENT_TOUCH_RELEASED, 0, null, x, y);
                touch_up_last = System.currentTimeMillis();
                break;
            case Canvas.ACTION_MOVE:
                app.eventAdd(Event.EVENT_TOUCH_MOVED, 0, null, x, y);
                break;
        }
        return true;
    }

    private boolean isDoubleClick() {
        return System.currentTimeMillis() - touch_up_last < 250;//mls
    }

    private boolean isLongClick() {
        return System.currentTimeMillis() - touch_down_last > 1000;//mls
    }

    private long touch_up_last;//время последнего отжатия
    private long touch_down_last;//время последнего нажатия
    private final long TOUCH_DOUBLE_CLICK_MLS = 100;//интервал между нажатиями засчитываемый как двойной клик


    public void backPressed() {
        destroy(true);
    }

    public void menuPressed() {
        Manager.log(String.valueOfC("Manager.menuPressed"));
    }

    // ------------------ SERVICES ------------------------
    public boolean isSuspended() {
        return isSuspended;
    }

    protected Display getDisplay() {
        return Display.getDisplay(midlet);
    }

    public void vibrate() {
        Display.getDisplay(midlet).vibrate(300);
    }

    public static void paused(long pause) {
        try {
            Thread.sleep(1);//иногда пролетает мгновенно и получаем ArithmeticException: / by zero
            Thread.sleep(pause);
            Thread.yield();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void platformRequest(String str) {
        boolean is_close = false;
        try {
            is_close = midlet.platformRequest(str, true);
        } catch (Exception e) {
        }
        Manager.paused(100);
        if (is_close) {
            runStop();
        }
    }

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public MIDlet getMidlet() {
        return midlet;
    }

    public void setMidlet(MIDlet midlet) {
        this.midlet = midlet;
    }

    public Application getApplication() {
        return app;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onLowMemory() {
    }

    public void draw() {
    }

    public void loadSplash() {
    }

    public int getFrameTotalCount() {
        return 0;
    }

    public void setOpenGLDrawCount(int drawCount) {
    }

    public int getSizeExpectedResources() {
        return 0;
    }

    public void initCanvas(MIDlet midlet) {
        canvas = new Canvas(this, 1, 1);
        if (app != null) {
            canvas.setRenderer(app.getGraphics());
        }
    }

    public static void log(String message) {
        Debug.log(message);
    }

    public static void logEx(Exception ex) {
        Debug.logEx(ex);
    }

}
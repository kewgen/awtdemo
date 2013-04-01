package com.geargames.awtdemo.application;

import com.geargames.common.logging.Debug;
import com.geargames.platform.MIDlet;
import com.geargames.common.String;
import com.geargames.platform.packer.Canvas;
import com.geargames.platform.packer.Display;
import com.geargames.common.Graphics;
import com.geargames.common.Event;
import com.geargames.awtdemo.awt.components.PPanelManager;

public final class Manager extends com.geargames.platform.Manager implements Runnable {

    public final static boolean DEBUG = true; // Главный дебаг, если false отключает все остальные дебаги

    // ***** Settings **************************************************************************************************

    private Application app;
    private MIDlet midlet;
    private volatile boolean running;
    private boolean isSuspended;//поток приостановлен
    private Thread thread;

    private byte okLabelN;
    private byte cancelLabelN;
    private boolean enabledOkKey = true;
    private boolean enabledCancelKey = true;

    // ----- Manage ----------------------------------------------------------------------------------------------------

    private static Manager self_manger;
    private Canvas canvas;

    private Manager(MIDlet midlet_) {
        initCanvas(midlet_);
        self_manger = this;
        midlet = midlet_;
        isSuspended = false;
    }

    public static Manager getInstance(MIDlet midlet_) {
        if (self_manger == null) self_manger = new Manager(midlet_);
        return self_manger;
    }

    public static Manager getInstance() {
        return self_manger;
    }

    public void create() {
        isSuspended = false;

        canvas.detectKeys();
        canvas.setFullScreenMode(true);
        app = Application.getInstance();
        loading();
    }

    @Override
    public void loading() {
        app.loading();
    }

    // ------------------ Screen ------------------------
    @Override
    public void paint(Graphics graphics) {
        if (Port.IS_CONSOLE) {
            graphics.onCache(5000);
        }
        try {
            if (app == null) {
                return; // Application еще не создан
            }
            graphics.drawImage(app.getBuffer(), Port.SCREEN_DX, Port.SCREEN_DY);
        } catch (Exception e) {
            Debug.error(String.valueOfC(""), e);
        }
        app.setIsDrawing(false);
    }

    public void repaintStart() {
        canvas.repaintStart();
        canvas.repaint();
        midlet.repaint();
    }

    //изменение размеров экранного буфера
    @Override
    public void resizeScreenBuffer(int w, int h) {
        if (app == null) {
            return;
        }
        Port.setWH(w, h);
        app.createScreenBuffer(w, h);
        PPanelManager.getInstance().onScreenResize();
    }

    // ----- MIDLET EVENTS HANDLERS ------------------------------------------------------------------------------------

    // Обработчик события startApp мидлета
    public void startApp() {
        isSuspended = false;
        create();
        if (Port.OPEN_GL && Port.IS_ANDROID) {//вызов потока аи и рендера делает OpenGL
            run_();
        } else {
            startMainThread();
        }
    }

    // Обработчик события pauseApp мидлета
    public void pauseApp() {
        isSuspended = true;
        Display.getDisplay(midlet).setCurrent(null);
    }

    @Override
    public void onStop() {
        destroy(true);
    }

    private boolean destroy_correct;

    // Обработчик события destroyApp мидлета
    public void destroy(boolean correct) {
        destroy_correct = correct;
        stopMainThread();
        if (Port.OPEN_GL && Port.IS_ANDROID) runStop();
        midlet.notifyDestroyed();
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
        Debug.debug(String.valueOfC("Manager.stopMainThread"));
    }

    public void run_() {
        Debug.debug(String.valueOfC("Manager.run_"));
        running = true;
        runStart();
    }

    @Override
    public void run() {
        Debug.debug(String.valueOfC("Manager.run - Main thread running"));
        running = true;
        try {
            runStart();
            while (getRunning()) {
                mainLoop();
            }
            runStop();
        } catch (Exception e) {
            Debug.error(String.valueOfC(""), e);
            stopMainThread();
            Debug.debug(String.valueOfC("Application.run").concatC(e.toString()));
        }
    }

    public boolean getRunning() {
        return running;
    }

    @Override
    public void mainLoop() {//Вызов основного игрового цикла. Он должен вызывать рендер нужных фреймов
        app.mainLoop();
    }

    protected final void runStart() {
        try {
            isSuspended = false;
            Display.getDisplay(midlet).setCurrent(this);
        } catch (Exception e) {
            Debug.error(String.valueOfC("onStart error [FILELINE]"), e);
        }
    }

    protected final void runStop() {
        try {
            Debug.debug(String.valueOfC("runStop"));
            app.onStop(true);
        } catch (Exception e) {
            Debug.error(String.valueOfC("Error during stop [FILELINE]"), e);
        }
    }


    // ------------KEYS CONTROL----------------

    private int pressedKey = 0;

    final public static byte SK_NONE = 7;

    @Override
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

    @Override
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
                    app.eventAdd(Event.EVENT_TOUCH_DOUBLE_CLICK, 0, null, x, y);
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

    @Override
    public void backPressed() {
        destroy(true);
    }

    @Override
    public void menuPressed() {
        Debug.debug(String.valueOfC("Manager.menuPressed"));
    }

    // ------------------ SERVICES ------------------------
    public boolean isSuspended() {
        return isSuspended;
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

    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    @Override
    public MIDlet getMidlet() {
        return midlet;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void loadSplash() {
    }

    @Override
    public int getFrameTotalCount() {
        return 0;
    }

    @Override
    public int getSizeExpectedResources() {
        return 0;
    }

    public void initCanvas(MIDlet midlet) {
        canvas = new Canvas(this, 1, 1);
        if (app != null) {
            canvas.setRenderer(app.getGraphics());
        }
    }

}
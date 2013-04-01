package com.geargames.awtdemo.application;

import com.geargames.common.logging.Debug;
import com.geargames.awt.Anchors;
import com.geargames.awt.TextHint;
import com.geargames.awtdemo.packer.PUnitCreator;
import com.geargames.common.timers.TimerManager;
import com.geargames.common.Event;
import com.geargames.common.env.Environment;
import com.geargames.common.packer.PFont;
import com.geargames.common.packer.PFontManager;
import com.geargames.common.util.ArrayIntegerDual;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.platform.packer.Graphics;
import com.geargames.platform.packer.Image;

import java.awt.Point;


public final class Application extends com.geargames.common.Application {

    public static final int FPS_MAXIMUM = 30;

    private Loader loader;
    private Render render;
    private PFontManager fontManager;
    private PPanelManager panels = PPanelManager.getInstance();

    private boolean isLoading = true; // true, если данные загружаются

    protected Graphics graphicsBuffer;
    protected Image i_buf;
    private boolean isDrawing;

    //TODO Переименовать stateInfo
    private String stateInfoString = null;
    private Point stateInfoPosition = new Point(12, 36);
    private byte stateInfoAnchors = Anchors.TOP_LEFT_ANCHOR;
    // splash
    private Image backgroundImage = createBackgroundImage();

    private static Application instance;

    // Конструктор
    public Application() {
        super();
        if (graphicsBuffer == null) {
            createScreenBuffer(Port.getW(), Port.getH());
        }
//        drawSplash();
    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
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
            Debug.critical("Exception during the creation of screen buffer", ex);
        }
    }

    private void drawSplash() {
        try {
            if (backgroundImage != null) {
                graphicsBuffer.drawImage(backgroundImage, 0, 0);
//                graphicsBuffer.drawImage(backgroundImage, i_buf.getWidth() / 2, i_buf.getHeight() / 2);
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
        } catch (Exception ex) {
            Debug.error("Exception during splash drawing", ex);
        }
    }

    public void drawSplash(java.lang.String str) {
        stateInfoString = str;
        drawSplash();
    }

    public void loading() {

        tSleep = System.currentTimeMillis();

        Debug.config("Memory total, free: " +
                /*Debug.formatSize*/(Manager.getTotalMemory()) + ", " +
                /*Debug.formatSize*/(Manager.getFreeMemory()));

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

        isLoading = false;

        TextHint textHint = TextHint.getInstance();
        textHint.setSkinObject(render.getObject(Graph.OBJ_HINT));
//        textHint.setDefaultFont(PFontCollection.getFontHint());

        panels.initiate(render);

        stateInfoString = "";
    }

    protected void onStop(boolean correct) {
        Debug.debug("Application.onStop");
    }

    public PFontManager getFontManager() {
        return fontManager;
    }

    // ---------------MAIN LOOP------------------

    @Override
    public void mainLoop() {
        try {
            if (Manager.getInstance().isSuspended() || isLoading) {
                Manager.paused(10);
                return;
            }
            eventProcess();
            TimerManager.update();

            draw(graphicsBuffer);

            if (true/* || this.equals(manager.getDisplay())*/) {
                isDrawing = true;
                /*ObjC uncomment*///is_drawing = false;
                Manager.getInstance().repaintStart();
                Thread.yield();
                while (isDrawing) {
                    Manager.paused(5);
                }
            }
            manageFPS(FPS_MAXIMUM);
        } catch (Exception e) {
            Debug.error("", e); //todo: Добавить сообщение об ошибке
        }
    }

    private int arr_tick = 0;
    private boolean arr_side = true;
    private long tSleep;
    private long fps_cur;

    public void manageFPS(int fps) {
        if (fps != 0) {
            int timeFPS = (1000 / fps);//задержка для установленного фпс
            long timeElapsed = Environment.currentTimeMillis() - tSleep;//реальная задержка
            long paused = timeFPS - timeElapsed;//делаем затержку для выдерживания фпс
            if (timeElapsed <= 0) {
                timeElapsed = 1;
            }
            if (paused > 0) {
                Manager.paused(paused);
            }
            fps_cur = (fps_cur + 1000 / (timeElapsed/* - (paused > 0 ? paused : 0)*/)) / 2;
            tSleep = Environment.currentTimeMillis();
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

    // --------------- MESSAGE HANDLERS --------------------------------------------------------------------------------

    /**
     * Выполнение всех манипуляций на один игровой тик
     */
    @Override
    protected void onEvent(Event event) {
//        Object element = event.getData();
//        if (element != null) {
//            ((Eventable)element).onEvent(event.getUid(), event.getParam(), event.getX(), event.getY());
//        } else {
            gameEvent(event.getUid(), event.getParam(), event.getX(), event.getY());
//        }
    }

    /**
     * Выполнение всех манипуляций на один игровой тик
     */
    public void gameEvent(int code, int param, int x, int y) {
        panels.onEvent(code, param, x, y);
    }

    // ----------------- DRAWING ---------------------------------------------------------------------------------------

    private void draw(Graphics graphics) {
        try {
            if (backgroundImage != null) {
//                backgroundImage.draw(graphics, 0, 0);
                graphics.drawImage(backgroundImage, 0, 0);
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
        } catch (Exception ex) {
            Debug.error("Drawing operation failed", ex);
        }
        /*ObjC uncomment*///if ([Port isOpenGL]) [gles_view paintEnd];
    }

    public Image getBuffer() {
        return i_buf;
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void setIsDrawing(boolean isDrawing) {
        this.isDrawing = isDrawing;
    }

    // -----------------------------------------------------------------------------------------------------------------

    protected Image createBackgroundImage()
    {
        try {
            return Image.createImage("/s1.png", Manager.getInstance());
        } catch (Exception ex) {
            Debug.error("Image loading failed", ex);
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

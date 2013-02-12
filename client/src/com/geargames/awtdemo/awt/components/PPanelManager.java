package com.geargames.awtdemo.awt.components;

import com.geargames.awt.*;
import com.geargames.common.Graphics;
import com.geargames.common.Render;
import com.geargames.awtdemo.awt.components.main.*;

import com.geargames.common.util.ArrayList;

/**
 * User: mikhail v. kutuzov
 * Класс создан, для создания и настройки объектов панелей верхнего уровня, а так же для доступа к этим объектам и
 * отображением их на экране.
 * Работает в однопоточном режиме.
 */
public class PPanelManager {
    private static PPanelManager instance;

    private ArrayList drawableElements;
    private ArrayList callableElements;
    private ArrayList modalElements;

    private ArrayList preDeafElements;
    private ArrayList preHideElements;

    private boolean hideAll;

    private PPanelManager() {
        drawableElements = new ArrayList();
        callableElements = new ArrayList();
        preDeafElements = new ArrayList();
        preHideElements = new ArrayList();
        modalElements = new ArrayList();
        hideAll = false;
    }

    public static PPanelManager getInstance() {
        if (instance == null) {
            instance = new PPanelManager();
        }
        return instance;
    }

    /**
     * Вызывать в цикле доставки событий.
     *
     * @param code
     * @param param
     * @param x
     * @param y
     */
    public void event(int code, int param, int x, int y) {
        if (modalElements.isEmpty()) {
            if (!preDeafElements.isEmpty()) {
                callableElements.removeAll(preDeafElements);
                preDeafElements.clear();
            }
            int size = callableElements.size();
            for (int i = 0; i < size; i++) {
                Drawable element = (Drawable) callableElements.get(i);
                element.event(code, param, x, y);
            }
        } else {
//            if (code == Event.EVENT_TICK) {
//                if (Drawable.DEBUG) {
//                    Debug.trace("TICK-TACK");
//                }
//            }
            Drawable modal = (Drawable) modalElements.get(modalElements.size()-1);
            modal.event(code, param, x, y);
        }

        // TODO TextHint должен отрисовываться через список drawableElements, а не в индивидуальном порядке
        TextHint hintElement = TextHint.getInstance();
//        if (hintElement.getVisible())
        hintElement.event(code, param, x, y);
    }

    /**
     * Вызывать в цикле рисования.
     *
     * @param graphics
     */
    public void draw(Graphics graphics) {
        if (hideAll) {
            drawableElements.clear();
            callableElements.clear();
            preHideElements.clear();
            preDeafElements.clear();
//            modalElements.clear();
            hideAll = false;
        } else {
            if (!preHideElements.isEmpty()) {
                drawableElements.removeAll(preHideElements);
                modalElements.removeAll(preHideElements);
                preHideElements.clear();
            }
            int size = drawableElements.size();
            for (int i = 0; i < size; i++) {
                Drawable element = (Drawable) drawableElements.get(i);
                element.draw(graphics);
            }
        }

        // TODO TextHint должен отрисовываться через список drawableElements, а не в индивидуальном порядке
        TextHint hintElement = TextHint.getInstance();
//        if (hintElement.getVisible())
            hintElement.draw(graphics);
    }

    public void onScreenResize() {
        int size = drawableElements.size();
        for (int i = 0; i < size; i++) {
            DrawablePPanel panel = (DrawablePPanel) drawableElements.get(i);
            panel.init();
        }
    }

    /**
     * Рисовать панель на графическом контексте.
     *
     * @param panel
     */
    public void show(DrawablePPanel panel) {
        // TODO Удалить element из preDeafElements и preHideElements
        // TODO А если панелька уже присутствует в списках?
        drawableElements.add(panel);
        callableElements.add(panel);
        panel.init();
        panel.onShow();
    }

    /**
     * Скрыть панель на графическом контексте.
     *
     * @param panel
     */
    public void hide(DrawablePPanel panel) {
        // TODO А если панелька уже присутствует в списках?
        int index = modalElements.indexOf(panel);
        if (index >= 0) {
            modalElements.remove(index);
            preHideElements.add(panel);
        } else {
            preDeafElements.add(panel);
        }
        panel.onHide();
    }

    public void hideAll() {
        hideAll = true;
        modalElements.clear();
        // TODO
//        preHideElements.clear();
//        preHideElements.addAll(drawableElements);
//        preDeafElements.clear();
//        preDeafElements.addAll(callableElements);
    }

    /**
     * Показать в модальном режиме.
     *
     * @param panel
     */
    public void showModal(DrawablePPanel panel) {
        // TODO Удалить element из preDeafElements и preHideElements
        // TODO А если модальная панелька уже присутствует в списках?
        modalElements.add(panel);
        drawableElements.add(panel);
        panel.init();
        panel.onShow();
    }

    /**
     * Скрыть модальный элемент. Перейти в обычный режим работы.
     */
//    public void hideModal() {
//        // TODO А если модальная панелька уже присутствует в списках на сокрытие?
//        int size = modalElements.size();
//        if (size > 0) {
//            DrawablePPanel panel = (DrawablePPanel) modalElements.remove(size - 1);
//            preHideElements.add(panel);
//            panel.onHide();
//        } else {
//            // TODO Возбудить исключение?
//        }
//    }

    /**
     * Работаем в модальном режиме?
     *
     * @return
     */
    public boolean isModal() {
        return modalElements.size() > 0;
    }

    /**
     * Настроить подчинённые окошки.
     *
     * @param render
     * @return
     */
    public boolean initiate(Render render) {
        if (render == null) {
            return false;
        }

        PMainPanel mainPanel = new PMainPanel();
        mainPanel.show();

        return true;
    }
}

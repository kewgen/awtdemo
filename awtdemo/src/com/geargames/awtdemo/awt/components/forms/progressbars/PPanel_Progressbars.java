package com.geargames.awtdemo.awt.components.forms.progressbars;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.awtdemo.timers.TimerIdMap;
import com.geargames.common.timers.TimerManager;
import com.geargames.common.timers.TimerListener;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 15.02.13
*/
public class PPanel_Progressbars extends DrawablePPanel implements TimerListener {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 109:
                    // Заголовок окна
                    PSimpleLabel caption = new PSimpleLabel(index);
                    caption.setText("PROGRESS BARS");
                    caption.setFont(PFontCollection.getFontFormTitle());
                    addPassiveChild(caption, index);
                    break;
            }
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (parentPrototype.getIndexes().indexOf(index)) {
                // Игнорируем некоторые элементы формы пакера
                case 1:
                case 3:
                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
            }
        }
    }

    PContentPanelImpl сontentPanel;
    PEntitledClosePanelButton buttonClose;
    PSimpleIndicator simpleIndicator1;
    PSimpleIndicator simpleIndicator2;
//    PHintProgressIndicator hintProgressIndicator;

    public PPanel_Progressbars() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_Progressbars(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

//----- Элементы формы -------------------------------------------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();
        label1.setText("P SIMPLE INDICATOR");
        label1.setFont(PFontCollection.getFontLabel());
        addChild(label1, 20, 40);

        PObject prototypeIndicator1 = Application.getInstance().getRender().getObject(Graph.OBJ_IND_1);
        PObject prototypeIndicator2 = Application.getInstance().getRender().getObject(Graph.OBJ_IND_CHARACTER);

        simpleIndicator1 = new PSimpleIndicator(prototypeIndicator1);
        addChild(simpleIndicator1, 200, 40);

        simpleIndicator2 = new PSimpleIndicator(prototypeIndicator2);
        addChild(simpleIndicator2, 200, 70);

//        hintProgressIndicator = new PHintProgressIndicator(prototypeIndicator, ...);
//        addChild(simpleIndicator, 200, 40);

        TimerManager.setPeriodicTimer(TimerIdMap.AWTDEMO_PROGRESSBARS_TICK, 500, this);
    }

    @Override
    public void onShow() {
    }

    @Override
    public void onHide() {
        TimerManager.killTimer(TimerIdMap.AWTDEMO_PROGRESSBARS_TICK);
    }

    private int tickCount = 0;
    private final int MAX_TICK_COUNT = 20;

    /**
     * Метод вызывается каждый раз при срабатывании таймера.
     * @param timerId - идентификатор сработавшего таймера, который вызвал данный метод.
     */
    @Override
    public void onTimer(int timerId) {
        if (timerId == TimerIdMap.AWTDEMO_PROGRESSBARS_TICK) {
            simpleIndicator1.setValue((int)(((float)tickCount / MAX_TICK_COUNT) * simpleIndicator1.getCardinality()));
            simpleIndicator2.setValue((int)(((float)tickCount / MAX_TICK_COUNT) * simpleIndicator2.getCardinality()));
            tickCount++;
            if (tickCount > MAX_TICK_COUNT) {
                tickCount = 0;
            }
        }
    }

}
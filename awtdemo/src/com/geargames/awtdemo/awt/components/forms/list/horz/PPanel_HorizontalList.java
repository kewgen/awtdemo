package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awt.utils.ScrollListener;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.application.Render;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStrictlyClipped;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStuck;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.common.util.ArrayList;

/**
 * User: abarakov
 * Date: 12.02.13
*/
//PScrollableComponentsPanel
public class PPanel_HorizontalList extends DrawablePPanel {

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
                    caption.setFont(PFontCollection.getFontFormTitle());
                    caption.setText(String.valueOfC("ГОРИЗОНТАЛЬНЫЙ СПИСОК"));
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

    protected class PHorizontalScrollBar extends PSpriteProgressIndicator implements ScrollListener {

        public PHorizontalScrollBar(PObject prototype) {
            super(prototype);
        }

        @Override
        public void onPositionChanged() {
            setPercentage(horizontalList.getScrollPercent());
        }
    }

    PContentPanelImpl сontentPanel;
    HorizontalList horizontalList;
    PHorizontalScrollBar scrollBar;
    PEntitledClosePanelButton buttonClose;

    public PPanel_HorizontalList() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 231, Y - 163);
    }

    public PPanel_HorizontalList(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

//----- Компонент "Горизонтальный список" ------------------------------------------------------------------------------

        ArrayList frames = new ArrayList();
        Render render = Application.getInstance().getRender();
        frames.add(render.getFrame(1324)); // ?
        frames.add(render.getFrame(1320)); // бугай
        frames.add(render.getFrame(1322)); // офицер
        frames.add(render.getFrame(1324)); // ?
        frames.add(render.getFrame(1324)); // ?
        frames.add(render.getFrame(1320)); // бугай
        frames.add(render.getFrame(1322)); // офицер
        frames.add(render.getFrame(1324)); // ?
        frames.add(render.getFrame(1324)); // ?
        frames.add(render.getFrame(1322)); // офицер
        frames.add(render.getFrame(1320)); // бугай

        PObject listPrototype = render.getObject(Graph.OBJ_LIST_FIGHTER);
        horizontalList = new HorizontalList(frames, listPrototype);
        addChild(horizontalList, 0+229, 0+14);

//----- Компонент "Полоса прокрутки" для списка ------------------------------------------------------------------------

        PObject prototypeIndicator1 = Application.getInstance().getRender().getObject(Graph.OBJ_IND_1);

        scrollBar = new PHorizontalScrollBar(prototypeIndicator1);
        addChild(scrollBar, 9, 85);

//----- Кнопки настройки Горизонтального списка ------------------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();
        label1.setText(String.valueOfC("LISTENER:"));
        label1.setFont(PFontCollection.getFontLabel());
        addChild(label1, 9, 100);

        PRadioGroup radioGroup = new PRadioGroup(4);

        // Четыре кнопки выбора соответствующих MotionListener
        PRadioButton buttonListener1 = new PButton_Horizontal_CenteredElasticInertMotionListener(
                this, Application.getInstance().getRender().getObject(61));
        buttonListener1.setChecked(true);
        buttonListener1.onClick();
        radioGroup.addButton(buttonListener1);
        addChild(buttonListener1, 7, 112);

        PRadioButton buttonListener2 = new PButton_Horizontal_InertMotionListener(
                this, Application.getInstance().getRender().getObject(62));
        radioGroup.addButton(buttonListener2);
        addChild(buttonListener2, 60, 112);

        PRadioButton buttonListener3 = new PButton_Horizontal_ElasticInertMotionListener(
                this, Application.getInstance().getRender().getObject(63));
        radioGroup.addButton(buttonListener3);
        addChild(buttonListener3, 113, 112);

        PRadioButton buttonListener4 = new PButton_Horizontal_StubMotionListener(
                this, Application.getInstance().getRender().getObject(64));
        radioGroup.addButton(buttonListener4);
        addChild(buttonListener4, 166, 112);

        // Кнопка-переключатель возможности скроллить список с помощью касаний
        PToggleButton buttonStuck = new PButton_ToggleStuck(
                horizontalList, Application.getInstance().getRender().getObject(61));
        addChild(buttonStuck, 235, 112);

        // Кнопка-переключатель режима отсечения крайних элементов списка
        PToggleButton buttonStrictlyClipped = new PButton_ToggleStrictlyClipped(
                horizontalList, Application.getInstance().getRender().getObject(62));
        addChild(buttonStrictlyClipped, 288, 112);

//        PDummyEntitledTouchButton button1 = new PDummyEntitledTouchButton(String.valueOfC("TOUCH"));

    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

    public HorizontalList getHorizontalList() {
        return horizontalList;
    }

    public PHorizontalScrollBar getScrollBar() {
        return scrollBar;
    }

}
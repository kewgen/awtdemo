package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.app.Graph;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.app.Render;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStrictlyClipped;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStuck;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.awtdemo.app.Application;
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

        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 19:
                    // Заголовок окна
                    PSimpleLabel caption = new PSimpleLabel(index);
                    caption.setFont(PFontCollection.getFontFormTitle());
                    caption.setText(String.valueOfC("ГОРИЗОНТАЛЬНЫЙ СПИСОК"));
                    addPassiveChild(caption, index);
                    break;
                default:
//                    super.createDefaultElementByIndex(index);
                    break;
            }
        }

        protected void createDefaultElementByIndex(IndexObject index) {
            switch (index.getSlot()) {
                // Игнорируем некоторые элементы формы пакера
                case 0:
                case 2:
                    break;
                default:
                    super.createDefaultElementByIndex(index);
            }
        }
    }

    PContentPanelImpl сontentPanel;
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
        frames.add(render.getFrame(596)); // ?
        frames.add(render.getFrame(593)); // бугай
        frames.add(render.getFrame(595)); // офицер
        frames.add(render.getFrame(596)); // ?
        frames.add(render.getFrame(596)); // ?
        frames.add(render.getFrame(593)); // бугай
        frames.add(render.getFrame(595)); // офицер
        frames.add(render.getFrame(596)); // ?
        frames.add(render.getFrame(596)); // ?
        frames.add(render.getFrame(595)); // офицер
        frames.add(render.getFrame(593)); // бугай

        PObject listPrototype = render.getObject(Graph.OBJ_LIST_FIGHTER);
        HorizontalList horizontalList = new HorizontalList(frames, listPrototype);
        addChild(horizontalList, 0+229, 0+14);

//----- Кнопки настройки Горизонтального списка ------------------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();
        label1.setText(String.valueOfC("LISTENER:"));
        label1.setFont(PFontCollection.getFontLabel());
        addChild(label1, 9, 100);

        PRadioGroup radioGroup = new PRadioGroup(4);

        // Четыре кнопки выбора соответствующих MotionListener
        PRadioButton buttonListener1 = new PButton_Horizontal_CenteredElasticInertMotionListener(
                horizontalList, Application.getInstance().getRender().getObject(58));
        buttonListener1.setState(true);
        radioGroup.addButton(buttonListener1);
        addChild(buttonListener1, 7, 112);

        PRadioButton buttonListener2 = new PButton_Horizontal_InertMotionListener(
                horizontalList, Application.getInstance().getRender().getObject(59));
        radioGroup.addButton(buttonListener2);
        addChild(buttonListener2, 60, 112);

        PRadioButton buttonListener3 = new PButton_Horizontal_ElasticInertMotionListener(
                horizontalList, Application.getInstance().getRender().getObject(60));
        radioGroup.addButton(buttonListener3);
        addChild(buttonListener3, 113, 112);

        PRadioButton buttonListener4 = new PButton_Horizontal_StubMotionListener(
                horizontalList, Application.getInstance().getRender().getObject(61));
        radioGroup.addButton(buttonListener4);
        addChild(buttonListener4, 166, 112);

        // Кнопка-переключатель возможности скроллить список с помощью касаний
        PToggleButton buttonStuck = new PButton_ToggleStuck(
                horizontalList, Application.getInstance().getRender().getObject(58));
        addChild(buttonStuck, 235, 112);

        // Кнопка-переключатель режима отсечения крайних элементов списка
        PToggleButton buttonStrictlyClipped = new PButton_ToggleStrictlyClipped(
                horizontalList, Application.getInstance().getRender().getObject(59));
        addChild(buttonStrictlyClipped, 288, 112);

//        PDummyEntitledTouchButton button1 = new PDummyEntitledTouchButton(String.valueOfC("TOUCH"));

    }

    public void onHide() {
    }

    public void onShow() {
    }

}
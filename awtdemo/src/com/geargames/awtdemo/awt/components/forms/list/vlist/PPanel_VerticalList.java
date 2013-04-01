package com.geargames.awtdemo.awt.components.forms.list.vlist;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStrictlyClipped;
import com.geargames.awtdemo.awt.components.forms.list.PButton_ToggleStuck;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 19.02.13
*/
public class PPanel_VerticalList extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
//                case 13:
//                    // Кнопка закрытия окна
//                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
//                    addActiveChild(buttonClose, index);
//                    break;
//                case 109:
//                    // Заголовок окна
//                    PSimpleLabel caption = new PSimpleLabel(index);
//                    caption.setFont(PFontCollection.getFontFormTitle());
//                    caption.setData("ВЕРТИКАЛЬНЫЙ СПИСОК");
//                    addActiveChild(caption, index);
//                    break;
                // Игнорируем некоторые элементы формы пакера
                case 0:
                case 50:
                case 51:
                case 52:
                    break;
                case 53:
                    // Вертикальный список
                    verticalList = new VerticalList(7, (PObject)index.getPrototype());
                    addActiveChild(verticalList, index);
                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
                    break;
            }
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
//                // Игнорируем некоторые элементы формы пакера
//                case 0:
//                case 2:
//                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
            }
        }
    }

    PContentPanelImpl сontentPanel;
//    PEntitledClosePanelButton buttonClose;
    VerticalList verticalList;

    public PPanel_VerticalList() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_FIGHTER));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 338, Y - 221);
    }

    public PPanel_VerticalList(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        // Кнопка закрытия окна
        PEntitledClosePanelButton buttonClose = new PEntitledClosePanelButton();
        addChild(buttonClose, 7, 375);
        buttonClose.setParent(this);

//----- Кнопки настройки Вертикального списка ------------------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();
        label1.setText("LISTENER:");
        label1.setFont(PFontCollection.getFontLabel());
        addChild(label1, 9, 30);

        PRadioGroup radioGroup = new PRadioGroup(4);

        // Четыре кнопки выбора соответствующих MotionListener
        PRadioButton buttonListener1 = new PButton_Vertical_CenteredElasticInertMotionListener(
                verticalList, Application.getInstance().getRender().getObject(61));
        radioGroup.addButton(buttonListener1);
        addChild(buttonListener1, 7, 42);

        PRadioButton buttonListener2 = new PButton_Vertical_InertMotionListener(
                verticalList, Application.getInstance().getRender().getObject(62));
        radioGroup.addButton(buttonListener2);
        addChild(buttonListener2, 60, 42);

        PRadioButton buttonListener3 = new PButton_Vertical_ElasticInertMotionListener(
                verticalList, Application.getInstance().getRender().getObject(63));
        radioGroup.addButton(buttonListener3);
        buttonListener3.setChecked(true);
        addChild(buttonListener3, 113, 42);

        PRadioButton buttonListener4 = new PButton_Vertical_StubMotionListener(
                verticalList, Application.getInstance().getRender().getObject(64));
        radioGroup.addButton(buttonListener4);
        addChild(buttonListener4, 166, 42);

        // Кнопка-переключатель возможности скроллить список с помощью касаний
        PToggleButton buttonStuck = new PButton_ToggleStuck(
                verticalList, Application.getInstance().getRender().getObject(61));
        addChild(buttonStuck, 7, 112);

        // Кнопка-переключатель режима отсечения крайних элементов списка
        PToggleButton buttonStrictlyClipped = new PButton_ToggleStrictlyClipped(
                verticalList, Application.getInstance().getRender().getObject(62));
        addChild(buttonStrictlyClipped, 60, 112);

//        PDummyEntitledTouchButton button1 = new PDummyEntitledTouchButton("TOUCH");
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
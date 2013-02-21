package com.geargames.awtdemo.awt.components.forms.buttons;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledRadioButton;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledToggleButton;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 14.02.13
*/
public class PPanel_Buttons extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        private PRadioGroup radioGroup;

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            PElement element = null;
            switch (index.getSlot()) {
                case 1:
                    element = new PDummyTouchButton((PObject) index.getPrototype());
                    break;
                case 2:
                    element = new PDummyTouchButton((PObject) index.getPrototype());
                    break;
                case 3:
                    element = new PDummyEntitledTouchButton(String.valueOfC("TOUCH"));
                    break;
                case 5:
                    element = new PDummyToggleButton((PObject) index.getPrototype());
                    break;
                case 6:
                    element = new PDummyToggleButton((PObject) index.getPrototype());
                    break;
                case 7:
                    element = new PDummyEntitledToggleButton(String.valueOfC("TOGGLE"));
                    break;
                case 9:
                    element = new PDummyRadioButton((PObject) index.getPrototype());
                    if (radioGroup == null)
                        radioGroup = new PRadioGroup(4);
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 10:
                    element = new PDummyRadioButton((PObject) index.getPrototype());
                    if (radioGroup == null)
                        radioGroup = new PRadioGroup(4);
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 11:
                    element = new PDummyEntitledRadioButton(String.valueOfC("RADIO"));
                    if (radioGroup == null)
                        radioGroup = new PRadioGroup(4);
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    element = buttonClose;
                    break;
                case 15:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText(String.valueOfC("TOUCH BUTTONS"));
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    break;
                case 16:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText(String.valueOfC("TOGGLE BUTTONS"));
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    break;
                case 17:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText(String.valueOfC("RADIO BUTTONS"));
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    break;
                case 19:
                    // Заголовок окна
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText(String.valueOfC("КНОПКИ"));
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontFormTitle());
                    break;
                default:
//                    super.createDefaultElementByIndex(index);
                    break;
            }
            if (element != null) {
                addActiveChild(element, index);
            }
        }

        protected void createDefaultElementByIndex(IndexObject index) {
            switch (index.getSlot()) {
                // Игнорируем некоторые элементы формы пакера
                case 0:
                    break;
                default:
                    super.createDefaultElementByIndex(index);
            }
        }
    }

    PContentPanelImpl сontentPanel;
    PEntitledClosePanelButton buttonClose;

    public PPanel_Buttons() {
        this(Application.getInstance().getRender().getObject(12));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_Buttons(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);
    }

    public void onHide() {
    }

    public void onShow() {
    }
}
package com.geargames.awtdemo.awt.components.forms.buttons;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.*;
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

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            PElement element;
            switch (index.getSlot()) {
                case 1:
                    element = new PDummyTouchButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    break;
                case 2:
                    element = new PDummyTouchButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    break;
                case 3:
                    element = new PDummyEntitledTouchButton();
                    ((PDummyEntitledTouchButton)element).setText("TOUCH");
                    addActiveChild(element, index);
                    break;
                case 5:
                    element = new PDummyToggleButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    break;
                case 6:
                    element = new PDummyToggleButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    break;
                case 7:
                    element = new PDummyEntitledToggleButton();
                    ((PDummyEntitledToggleButton)element).setText("TOGGLE");
                    addActiveChild(element, index);
                    break;
                case 9:
                    element = new PDummyRadioButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    if (radioGroup == null) {
                        radioGroup = new PRadioGroup(4);
                    }
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 10:
                    element = new PDummyRadioButton((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    if (radioGroup == null) {
                        radioGroup = new PRadioGroup(4);
                    }
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 11:
                    element = new PDummyEntitledRadioButton();
                    ((PDummyEntitledRadioButton)element).setText("RADIO");
                    addActiveChild(element, index);
                    if (radioGroup == null) {
                        radioGroup = new PRadioGroup(4);
                    }
                    radioGroup.addButton((PRadioButton)element);
                    break;
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 15:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText("TOUCH BUTTONS");
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    addPassiveChild(element, index);
                    break;
                case 16:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText("TOGGLE BUTTONS");
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    addPassiveChild(element, index);
                    break;
                case 17:
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText("RADIO BUTTONS");
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontLabel());
                    addPassiveChild(element, index);
                    break;
                case 109:
                    // Заголовок окна
                    element = new PSimpleLabel(index);
                    ((PSimpleLabel)element).setText("BUTTONS");
                    ((PSimpleLabel)element).setFont(PFontCollection.getFontFormTitle());
                    addPassiveChild(element, index);
                    break;
            }
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (parentPrototype.getIndexes().indexOf(index)) {
                // Игнорируем некоторые элементы формы пакера
                case 1:
                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
            }
        }
    }

    PContentPanelImpl сontentPanel;
    PEntitledClosePanelButton buttonClose;

    public PPanel_Buttons() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
    }

    public PPanel_Buttons(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
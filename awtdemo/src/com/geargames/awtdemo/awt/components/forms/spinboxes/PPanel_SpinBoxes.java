package com.geargames.awtdemo.awt.components.forms.spinboxes;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Graph;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 19.02.13
*/
public class PPanel_SpinBoxes extends DrawablePPanel {

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
                    caption.setText(String.valueOfC("SPIN BOXES"));
                    caption.setFont(PFontCollection.getFontFormTitle());
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

    public PPanel_SpinBoxes() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_SpinBoxes(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

//----- Элементы формы -------------------------------------------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();
        label1.setText(String.valueOfC("P GRADUAL SPIN BOX"));
        label1.setFont(PFontCollection.getFontLabel());
        addChild(label1, 20, 40);

        PObject prototypeIndicator = Application.getInstance().getRender().getObject(Graph.OBJ_SPINBOX);

        PGradualSpinBox gradualSpinBox = new PGradualSpinBox(prototypeIndicator);
        gradualSpinBox.setValue((short)0);
        gradualSpinBox.setFps(Application.mult_fps * 2);
        gradualSpinBox.setFont(PFontCollection.getFontLabel());
        addChild(gradualSpinBox, 270, 20);

        PSimpleLabel label2 = new PSimpleLabel();
        label2.setText(String.valueOfC("P STEP SPIN BOX"));
        label2.setFont(PFontCollection.getFontLabel());
        addChild(label2, 20, 90);

        PStepSpinBox stepSpinBox = new PStepSpinBox(prototypeIndicator);
        stepSpinBox.setValue((short)0);
        stepSpinBox.setFont(PFontCollection.getFontLabel());
        addChild(stepSpinBox, 270, 70);
    }

    public void onHide() {
    }

    public void onShow() {
    }
}
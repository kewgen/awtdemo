package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.spinboxes.PPanel_SpinBoxes;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_SpinBoxes extends PDummyEntitledTouchButton {

    public PButton_SpinBoxes() {
        super();
    }

    @Override
    public void onClick() {
        PPanel_SpinBoxes panel = new PPanel_SpinBoxes();
        panel.showModal();
    }
}
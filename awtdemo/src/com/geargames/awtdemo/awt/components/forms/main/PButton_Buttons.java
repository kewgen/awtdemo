package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.buttons.PPanel_Buttons;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PButton_Buttons extends PDummyEntitledTouchButton {

    public PButton_Buttons() {
        super();
    }

    @Override
    public void onClick() {
        PPanel_Buttons panel = new PPanel_Buttons();
        panel.showModal();
    }
}
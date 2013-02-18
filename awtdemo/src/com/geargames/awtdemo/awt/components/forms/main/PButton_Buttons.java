package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.buttons.PPanel_Buttons;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PButton_Buttons extends PDummyEntitledTouchButton {

    public PButton_Buttons(String caption) {
        super(caption);
    }

    public void action() {
        PPanel_Buttons panel = new PPanel_Buttons();
        panel.showModal();
    }
}
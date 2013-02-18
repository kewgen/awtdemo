package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.progressbars.PPanel_Progressbars;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 15.02.13
 */
public class PButton_Progressbars extends PDummyEntitledTouchButton {

    public PButton_Progressbars(String caption) {
        super(caption);
    }

    public void action() {
        PPanel_Progressbars panel = new PPanel_Progressbars();
        panel.showModal();
    }
}
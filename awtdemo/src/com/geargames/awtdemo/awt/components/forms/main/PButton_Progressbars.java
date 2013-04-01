package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.progressbars.PPanel_Progressbars;

/**
 * User: abarakov
 * Date: 15.02.13
 */
public class PButton_Progressbars extends PDummyEntitledTouchButton {

    public PButton_Progressbars() {
        super();
    }

    @Override
    public void onClick() {
        PPanel_Progressbars panel = new PPanel_Progressbars();
        panel.showModal();
    }
}
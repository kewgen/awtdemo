package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 14.02.13
 */
public class PButton_Exit extends PDummyEntitledTouchButton {

    public PButton_Exit(String caption) {
        super(caption);
    }

    public void onClick() {
        System.exit(0);
    }
}
package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.hints.PPanel_Hints;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 28.02.13
 */
public class PButton_Hints extends PDummyEntitledTouchButton {

    public PButton_Hints(String caption) {
        super(caption);
    }

    public void onClick() {
        PPanel_Hints panel = new PPanel_Hints();
        panel.showModal();
    }
}
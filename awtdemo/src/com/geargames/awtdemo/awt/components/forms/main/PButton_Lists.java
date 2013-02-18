package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.lists.PPanel_Lists;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 11.02.13 11:45
 */
public class PButton_Lists extends PDummyEntitledTouchButton {

    public PButton_Lists(String caption) {
        super(caption);
    }

    public void action() {
        PPanel_Lists panel = new PPanel_Lists();
        panel.showModal();
    }
}
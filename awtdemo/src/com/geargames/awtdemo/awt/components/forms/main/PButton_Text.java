package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.texts.PPanel_Text;

/**
 * User: abarakov
 * Date: 14.02.13
 */
public class PButton_Text extends PDummyEntitledTouchButton {

    public PButton_Text() {
        super();
    }

    @Override
    public void onClick() {
        PPanel_Text panel = new PPanel_Text();
        panel.showModal();
    }
}
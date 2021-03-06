package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.menu.PMenuExamPanel;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PButton_MenuExam extends PDummyEntitledTouchButton {

    public PButton_MenuExam() {
        super();
    }

    @Override
    public void onClick() {
        PMenuExamPanel panel = new PMenuExamPanel();
        panel.showModal();
    }
}
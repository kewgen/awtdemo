package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.components.PTouchButton;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 11.02.13 11:45
 */
public class PButton1 extends PTouchButton {

    public PButton1(PObject prototype) {
        super(prototype);
    }

    public void action() {
        PScrollableComponentsPanel scrollableComponentsPanel = new PScrollableComponentsPanel();
        scrollableComponentsPanel.showModal();
    }
}
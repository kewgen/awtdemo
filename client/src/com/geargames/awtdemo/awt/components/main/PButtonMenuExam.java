package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.components.PTouchButton;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PButtonMenuExam extends PTouchButton {

    public PButtonMenuExam(PObject prototype) {
        super(prototype);
    }

    public void action() {
        PMenuExamPanel menuExamPanel = new PMenuExamPanel();
        menuExamPanel.showModal();
    }
}
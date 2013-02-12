package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.components.PTouchButton;
import com.geargames.common.packer.PObject;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.awt.components.DrawablePPanel;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PClosePanelButton extends PTouchButton {

    private DrawablePPanel parent;

    public PClosePanelButton(PObject prototype) {
        super(prototype);
    }

    public PClosePanelButton() {
        this(Application.getInstance().getRender().getObject(67));
    }

    public void setParent(DrawablePPanel parent) {
        this.parent = parent;
    }

    public void action() {
        parent.hide();
    }
}
package com.geargames.awtdemo.awt.components.common;

import com.geargames.awt.components.PTouchButton;
import com.geargames.awtdemo.app.Graph;
import com.geargames.common.packer.PObject;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.awt.components.DrawablePPanel;

/**
 * User: abarakov
 * Date: 11.02.13 11:55
 */
public class PButtonClosePanel extends PTouchButton {

    private DrawablePPanel parent;

    public PButtonClosePanel(PObject prototype) {
        super(prototype);
    }

    public PButtonClosePanel() {
        this(Application.getInstance().getRender().getObject(Graph.OBJ_BUT));
    }

    public void setParent(DrawablePPanel parent) {
        this.parent = parent;
    }

    public void onClick() {
        parent.hide();
    }
}
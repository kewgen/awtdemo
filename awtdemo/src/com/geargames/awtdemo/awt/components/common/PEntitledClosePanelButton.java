package com.geargames.awtdemo.awt.components.common;

import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 14.02.13
 */
public class PEntitledClosePanelButton extends PEntitledTouchButton {

    private DrawablePPanel parent;

    public PEntitledClosePanelButton(PObject prototype) {
        super(prototype);
        setText("CLOSE");
        setFont(PFontCollection.getFontButtonCaption());
    }

    public PEntitledClosePanelButton() {
        this(Application.getInstance().getRender().getObject(Graph.OBJ_BUT));
    }

    public void setParent(DrawablePPanel parent) {
        this.parent = parent;
    }

    @Override
    public void onClick() {
        parent.hide();
    }
}
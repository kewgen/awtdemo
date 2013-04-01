package com.geargames.awtdemo.awt.components.common;

import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 14.02.13
 */
public class PDummyEntitledTouchButton extends PEntitledTouchButton {

    public PDummyEntitledTouchButton(PObject prototype) {
        super(prototype);
        setFont(PFontCollection.getFontButtonCaption());
    }

    public PDummyEntitledTouchButton() {
        this(Application.getInstance().getRender().getObject(Graph.OBJ_BUT));
    }

    @Override
    public void onClick() {
    }
}
package com.geargames.awtdemo.awt.components.common;

import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Graph;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 14.02.13
 */
public class PDummyEntitledTouchButton extends PEntitledTouchButton {

    public PDummyEntitledTouchButton(PObject prototype, String caption) {
        super(prototype, caption);
        setFont(PFontCollection.getFontButtonCaption());
    }

    public PDummyEntitledTouchButton(String caption) {
        this(Application.getInstance().getRender().getObject(Graph.OBJ_BUT), caption);
    }

    public void action() {
    }
}
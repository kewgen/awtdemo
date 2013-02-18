package com.geargames.awtdemo.awt.components.common;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.PTouchButton;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Render;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 11.02.13 11:45
 */
public class PHintedButton extends PTouchButton {

    public PHintedButton(PObject prototype) {
        super(prototype);
    }

    public void action() {
//        TextHint textHint = TextHint.getInstance();
//        Render render = Application.getInstance().getRender();
//        textHint.setSkinObject(render.getFrame(675), render, 16, 24, 16, 24);

        TextHint.show(com.geargames.common.String.valueOfC("Line1\n" +
                "Line2 Long\n" +
                "Line3\n" +
                "Line4\n" +
                "Line5\n" +
                "Line6"), 260, 20);
//            }
    }
}
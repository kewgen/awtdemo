package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.TextArea;
import com.geargames.awt.TextHint;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Event;
import com.geargames.awtdemo.app.Render;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 08.02.13
 * Time: 19:17
 */
public class HintedTextArea extends TextArea {

    @Override
    public boolean event(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_PRESSED)
        {
            TextHint textHint = TextHint.getInstance();
            Render render = Application.getInstance().getRender();
            textHint.setSkinObject(render.getFrame(675), render, 16, 24, 16, 24);
//            if (this.getTouchRegion().isWithIn(x, y))
//            {
                TextHint.show(String.valueOfC("Line1\n" +
                        "Line2 Long\n" +
                        "Line3\n" +
                        "Line4\n" +
                        "Line5\n" +
                        "Line6"), 260, 20);
//            }
        }
        return super.event(code, param, x, y);
    }
}

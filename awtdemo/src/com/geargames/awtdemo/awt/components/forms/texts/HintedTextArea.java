package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.components.TextArea;
import com.geargames.awtdemo.app.Event;

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

        }
        return super.event(code, param, x, y);
    }
}

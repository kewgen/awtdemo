package com.geargames.awtdemo.awt.components.forms.hints;

import com.geargames.awt.TextHint;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 28.02.13
 */
public class PButton_ShowHint1 extends PDummyEntitledToggleButton {

    public PButton_ShowHint1(String text) {
        super(text);
    }

    public void action() {
        // Не требуется
    }

    public boolean event(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
            TextHint.show(String.valueOfC("ВСПЛЫВАЮЩАЯ ПОДСКАЗКА С ИСПОЛЬЗОВАНИЕМ СИСТЕМНОГО ШРИФТА И СТАНДАРТНЫМИ НАСТРОЙКАМИ"),
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight(), null);
        }
        return super.event(code, param, x, y);
    }
}
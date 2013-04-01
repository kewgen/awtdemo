package com.geargames.awtdemo.awt.components.forms.hints;

import com.geargames.awt.TextHint;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledToggleButton;
import com.geargames.common.Event;

/**
 * User: abarakov
 * Date: 28.02.13
 */
public class PButton_ShowHint1 extends PDummyEntitledToggleButton {

    public PButton_ShowHint1() {
        super();
    }

    @Override
    public void onClick() {
        // Не требуется
    }

    @Override
    public boolean onEvent(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
            TextHint.show("ВСПЛЫВАЮЩАЯ ПОДСКАЗКА С ИСПОЛЬЗОВАНИЕМ СИСТЕМНОГО ШРИФТА И СТАНДАРТНЫМИ НАСТРОЙКАМИ",
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight(),
                    null);
        }
        return super.onEvent(code, param, x, y);
    }
}
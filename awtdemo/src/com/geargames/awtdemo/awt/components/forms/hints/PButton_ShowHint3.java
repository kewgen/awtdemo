package com.geargames.awtdemo.awt.components.forms.hints;

import com.geargames.awt.TextHint;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 28.02.13
 */
public class PButton_ShowHint3 extends PDummyEntitledToggleButton {

    public PButton_ShowHint3() {
        super();
    }

    @Override
    public void onClick() {
        // Не требуется
    }

    @Override
    public boolean onEvent(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
            TextHint.show(String.valueOfC("БЫСТРО ВСПЛЫВАЮЩАЯ И МЕДЛЕННО СКРЫВАЮЩАЯСЯ ПОДСКАЗКА ИСПОЛЬЗУЮЩАЯ РАСТРОВЫЙ ШРИФТ"),
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight(),
                    150, 2000, 2000, PFontCollection.getFontHint(), false);
        }
        return super.onEvent(code, param, x, y);
    }
}
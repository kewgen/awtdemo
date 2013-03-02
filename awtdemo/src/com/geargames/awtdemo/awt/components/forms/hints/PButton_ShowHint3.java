package com.geargames.awtdemo.awt.components.forms.hints;

import com.geargames.awt.TextHint;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyEntitledToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 28.02.13
 */
public class PButton_ShowHint3 extends PDummyEntitledToggleButton {

    public PButton_ShowHint3(String text) {
        super(text);
    }

    public void action() {
        // Не требуется
    }

    public boolean event(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
            TextHint.show(String.valueOfC("БЫСТРО ВСПЛЫВАЮЩАЯ И МЕДЛЕННО СКРЫВАЮЩАЯСЯ ПОДСКАЗКА ИСПОЛЬЗУЮЩАЯ РАСТРОВЫЙ ШРИФТ"),
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight(),
                    150, 2000, 2000, PFontCollection.getFontHint(), false);
        }
        return super.event(code, param, x, y);
    }
}
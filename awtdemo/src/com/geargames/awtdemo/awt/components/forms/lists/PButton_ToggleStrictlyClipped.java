package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.ScrollableArea;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_ToggleStrictlyClipped extends PDummyToggleButton {

    private ScrollableArea scrollableArea;

    public PButton_ToggleStrictlyClipped(ScrollableArea scrollableArea, PObject prototype) {
        super(prototype);
        this.scrollableArea = scrollableArea;
    }

    public void action() {
        scrollableArea.setStrictlyClipped(this.isState());
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("Toggle StrictlyClipped"), x, y);
        return true;
    }

    public boolean event(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_LONG_CLICK) {
//            if (getTouchRegion().isWithIn(x, y) && !isState()) {
            longClick(
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight()
            );
//            }
            return false;
        }
        return super.event(code, param, x, y);
    }
}
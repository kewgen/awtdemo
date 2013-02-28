package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.ScrollableArea;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 21.02.13
 */
public class PButton_ToggleStrictlyClipped extends PDummyToggleButton {

    private ScrollableArea[] scrollableAreaList;

    public PButton_ToggleStrictlyClipped(ScrollableArea[] scrollableAreaList, PObject prototype) {
        super(prototype);
        this.scrollableAreaList = scrollableAreaList;
    }

    public void action() {
        for (int i = 0; i < scrollableAreaList.length; i++) {
            ScrollableArea scrollableArea = scrollableAreaList[i];
            scrollableArea.setStrictlyClipped(this.isState());
        }
    }

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("Toggle StrictlyClipped"), x, y);
    }

    public boolean event(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
//            if (getTouchRegion().isWithIn(x, y) && !isState()) {
            showHint(
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight()
            );
//            }
        }
        return super.event(code, param, x, y);
    }
}
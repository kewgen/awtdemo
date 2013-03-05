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
public class PButton_ToggleStuck extends PDummyToggleButton {

    private ScrollableArea[] scrollableAreaList;

    public PButton_ToggleStuck(ScrollableArea[] scrollableAreaList, PObject prototype) {
        super(prototype);
        this.scrollableAreaList = scrollableAreaList;
    }

    public void onClick() {
        for (int i = 0; i < scrollableAreaList.length; i++) {
            ScrollableArea scrollableArea = scrollableAreaList[i];
            scrollableArea.setStuck(this.getChecked());
        }
    }

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("Toggle stuck"), x, y);
    }

    public boolean onEvent(int code, int param, int x, int y) {
        if (code == Event.EVENT_TOUCH_RELEASED) {
//            if (getTouchRegion().isWithIn(x, y) && !isState()) {
            showHint(
                    PPanelManager.getInstance().getEventX() - x,
                    PPanelManager.getInstance().getEventY() - y + getDrawRegion().getHeight()
            );
//            }
        }
        return super.onEvent(code, param, x, y);
    }
}
package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.TextArea;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyToggleButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 21.02.13
 */
public class PButton_ToggleEllipsis extends PDummyToggleButton {

    private TextArea[] textAreaList;

    public PButton_ToggleEllipsis(TextArea[] textAreaList, PObject prototype) {
        super(prototype);
        this.textAreaList = textAreaList;
    }

    public void action() {
        for (int i = 0; i < textAreaList.length; i++) {
            TextArea textArea = textAreaList[i];
            textArea.setEllipsis(this.isState());
        }
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("Toggle ellipsis"), x, y);
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
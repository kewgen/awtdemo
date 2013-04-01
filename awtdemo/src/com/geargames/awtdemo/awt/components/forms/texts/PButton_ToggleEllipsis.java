package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.TextArea;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyToggleButton;
import com.geargames.common.Event;
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

    @Override
    public void onClick() {
        for (int i = 0; i < textAreaList.length; i++) {
            TextArea textArea = textAreaList[i];
            textArea.setEllipsis(this.getChecked());
        }
    }

    public void showHint(int x, int y) {
        TextHint.show("Toggle ellipsis", x, y);
    }

    @Override
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
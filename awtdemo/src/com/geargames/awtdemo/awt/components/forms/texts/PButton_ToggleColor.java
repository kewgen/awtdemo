package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awt.components.TextArea;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Graph;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 21.02.13
 */
public class PButton_ToggleColor extends PEntitledTouchButton {

    private TextArea[] textAreaList;
    private final int[] colorList = {
            //R G B
            0xffffff,
            0x0000ff,
            0x00ff00,
            0xff0000,
            0x000000
    };
    private int indexColor = 0;

    public PButton_ToggleColor(TextArea[] textAreaList, PObject prototype, String text) {
        super(prototype, text);
        setFont(PFontCollection.getFontButtonCaption());
        this.textAreaList = textAreaList;
    }

    public PButton_ToggleColor(TextArea[] textAreaList, String text) {
        this(textAreaList, Application.getInstance().getRender().getObject(Graph.OBJ_BUT), text);
        this.textAreaList = textAreaList;
    }

    public void action() {
        indexColor++;
        if (indexColor == colorList.length) {
            indexColor = 0;
        }
        for (int i = 0; i < textAreaList.length; i++) {
            TextArea textArea = textAreaList[i];
            textArea.setColor(colorList[indexColor]);
        }
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("Toggle color"), x, y);
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
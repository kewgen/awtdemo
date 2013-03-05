package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.TextHint;
import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awt.components.TextArea;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Graph;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.common.Event;
import com.geargames.common.Graphics;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 21.02.13
 */
public class PButton_ToggleFormat extends PEntitledTouchButton {

    private TextArea[] textAreaList;
    private static final int[] formatList = {
            Graphics.LEFT,
            Graphics.RIGHT,
            Graphics.HCENTER
    };
    private int indexFormat = 0;

    public PButton_ToggleFormat(TextArea[] textAreaList, PObject prototype, String text) {
        super(prototype, text);
        setFont(PFontCollection.getFontButtonCaption());
        this.textAreaList = textAreaList;
    }

    public PButton_ToggleFormat(TextArea[] textAreaList, String text) {
        this(textAreaList, Application.getInstance().getRender().getObject(Graph.OBJ_BUT), text);
        this.textAreaList = textAreaList;
    }

    public void onClick() {
        indexFormat++;
        if (indexFormat == formatList.length) {
            indexFormat = 0;
        }
        for (int i = 0; i < textAreaList.length; i++) {
            TextArea textArea = textAreaList[i];
            textArea.setFormat(formatList[indexFormat]);
        }
    }

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("Toggle format"), x, y);
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
package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.StubMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;
import com.geargames.packer.Graphics;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_Horizontal_StubMotionListener extends PDummyRadioButton {

    private HorizontalList horizontalList;

    public PButton_Horizontal_StubMotionListener(HorizontalList horizontalList, PObject prototype) {
        super(prototype);
        this.horizontalList = horizontalList;
    }

    public void action() {
        StubMotionListener motionListener = new StubMotionListener();
        horizontalList.setMotionListener(
                //todo: adjustStubMotionListener только для Vertical?
                ScrollHelper.adjustStubMotionListener(
                        motionListener, horizontalList.getDrawRegion(),
                        horizontalList.getShownItemsAmount(), horizontalList.getItemSize(), Graphics.LEFT
                )
        );
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("StubMotionListener"), x, y);
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
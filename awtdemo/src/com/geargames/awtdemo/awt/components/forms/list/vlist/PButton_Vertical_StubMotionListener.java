package com.geargames.awtdemo.awt.components.forms.list.vlist;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.StubMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.packer.PObject;
import com.geargames.platform.packer.Graphics;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_Vertical_StubMotionListener extends PDummyRadioButton {

    private VerticalList verticalList;

    public PButton_Vertical_StubMotionListener(VerticalList verticalList, PObject prototype) {
        super(prototype);
        this.verticalList = verticalList;
    }

    @Override
    public void onClick() {
        StubMotionListener motionListener = new StubMotionListener();
        verticalList.setMotionListener(
                ScrollHelper.adjustStubMotionListener(
                        motionListener, verticalList.getDrawRegion(),
                        verticalList.getShownItemsAmount(), verticalList.getItemSize(), Graphics.TOP
                )
        );
    }

    public void showHint(int x, int y) {
        TextHint.show("StubMotionListener", x, y);
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
package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.InertMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
import com.geargames.common.*;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 18.02.13
 */
public class PButton_Horizontal_InertMotionListener extends PDummyRadioButton {

    private HorizontalList horizontalList;

    public PButton_Horizontal_InertMotionListener(HorizontalList horizontalList, PObject prototype) {
        super(prototype);
        this.horizontalList = horizontalList;
    }

    public void action() {
        InertMotionListener motionListener = new InertMotionListener();
        horizontalList.setMotionListener(
                ScrollHelper.adjustHorizontalInertMotionListener(
                        motionListener, horizontalList.getDrawRegion(),
                        horizontalList.getShownItemsAmount(), horizontalList.getItemSize()
                )
        );
    }

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("InertMotionListener"), x, y);
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
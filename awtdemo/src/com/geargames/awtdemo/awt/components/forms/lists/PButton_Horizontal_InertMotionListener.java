package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awt.utils.motions.InertMotionListener;
import com.geargames.awtdemo.app.Application;
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

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("InertMotionListener"), x, y);
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
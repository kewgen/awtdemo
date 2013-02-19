package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.motions.ElasticInertMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_ElasticInertMotionListener extends PDummyRadioButton {

    private HorizontalList horizontalList;

    public PButton_ElasticInertMotionListener(HorizontalList horizontalList, PObject prototype) {
        super(prototype);
        this.horizontalList = horizontalList;
    }

    public void action() {
        ElasticInertMotionListener motionListener = new ElasticInertMotionListener();
        horizontalList.setMotionListener(motionListener);
//        horizontalList.setMotionListener(
//                ScrollHelper.adjustHorizontalInertMotionListener(
//                        motionListener, horizontalList.getDrawRegion(),
//                        horizontalList.getShownItemsAmount(), horizontalList.getItemSize()
//                )
//        );
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("ElasticInertMotionListener"), x, y);
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
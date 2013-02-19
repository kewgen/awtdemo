package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 18.02.13
 */
public class PButton_CenteredElasticInertMotionListener extends PDummyRadioButton { //PDummyEntitledTouchButton

//    public PButton_CenteredElasticInertMotionListener(String caption) {
//        super(caption);
//    }

    private HorizontalList horizontalList;

    public PButton_CenteredElasticInertMotionListener(HorizontalList horizontalList, PObject prototype) {
        super(prototype);
        this.horizontalList = horizontalList;
    }

    public void action() {
        CenteredElasticInertMotionListener motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        horizontalList.setMotionListener(
                ScrollHelper.adjustHorizontalCenteredMenuMotionListener(
                        motionListener, getDrawRegion(), horizontalList.getItemsAmount(),
                        horizontalList.getItemSize(), horizontalList.getPrototype().getDrawRegion().getMinX()
                )
        );
    }

    public boolean longClick(int x, int y) {
        TextHint.show(String.valueOfC("CenteredElasticInertMotionListener"), x, y);
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
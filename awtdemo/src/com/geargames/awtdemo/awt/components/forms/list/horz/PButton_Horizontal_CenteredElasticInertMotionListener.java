package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.String;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 18.02.13
 */
public class PButton_Horizontal_CenteredElasticInertMotionListener extends PDummyRadioButton {

    private HorizontalList horizontalList;

    public PButton_Horizontal_CenteredElasticInertMotionListener(HorizontalList horizontalList, PObject prototype) {
        super(prototype);
        this.horizontalList = horizontalList;
    }

    public void action() {
        CenteredElasticInertMotionListener motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        horizontalList.setMotionListener(
                ScrollHelper.adjustHorizontalCenteredMenuMotionListener(
                        motionListener, horizontalList.getDrawRegion(), horizontalList.getItemsAmount(),
                        horizontalList.getItemSize(), horizontalList.getPrototype().getDrawRegion().getMinX()
                )
        );
    }

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("CenteredElasticInertMotionListener"), x, y);
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
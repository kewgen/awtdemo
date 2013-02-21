package com.geargames.awtdemo.awt.components.forms.lists;

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
 * Date: 19.02.13
 */
public class PButton_Vertical_CenteredElasticInertMotionListener extends PDummyRadioButton {

    private VerticalList verticalList;

    public PButton_Vertical_CenteredElasticInertMotionListener(VerticalList verticalList, PObject prototype) {
        super(prototype);
        this.verticalList = verticalList;
    }

    public void action() {
        CenteredElasticInertMotionListener motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        verticalList.setMotionListener(
                //todo: Изменить Horizontal -> Vertical
                ScrollHelper.adjustHorizontalCenteredMenuMotionListener(
                        motionListener, verticalList.getDrawRegion(), verticalList.getItemsAmount(),
                        verticalList.getItemSize(), verticalList.getPrototype().getDrawRegion().getMinY()
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
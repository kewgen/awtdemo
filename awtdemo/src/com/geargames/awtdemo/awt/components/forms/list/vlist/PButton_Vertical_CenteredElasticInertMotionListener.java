package com.geargames.awtdemo.awt.components.forms.list.vlist;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyRadioButton;
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

    @Override
    public void onClick() {
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

    public void showHint(int x, int y) {
        TextHint.show(String.valueOfC("CenteredElasticInertMotionListener"), x, y);
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
package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.awt.components.forms.buttons.PDummyRadioButton;
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
}
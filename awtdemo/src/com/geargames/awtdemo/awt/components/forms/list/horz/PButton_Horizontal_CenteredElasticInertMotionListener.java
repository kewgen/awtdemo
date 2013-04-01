package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.TextHint;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awtdemo.awt.components.PPanelManager;
import com.geargames.awtdemo.awt.components.common.PDummyRadioButton;
import com.geargames.common.Event;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 18.02.13
 */
public class PButton_Horizontal_CenteredElasticInertMotionListener extends PDummyRadioButton {

    private PPanel_HorizontalList panelOwner;

    public PButton_Horizontal_CenteredElasticInertMotionListener(PPanel_HorizontalList panelOwner, PObject prototype) {
        super(prototype);
        this.panelOwner = panelOwner;
    }

    @Override
    public void onClick() {
        CenteredElasticInertMotionListener motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        motionListener.setScrollListener(panelOwner.getScrollBar());
        panelOwner.getHorizontalList().setMotionListener(
                ScrollHelper.adjustHorizontalCenteredMenuMotionListener(
                        motionListener,
                        panelOwner.getHorizontalList().getDrawRegion(),
                        panelOwner.getHorizontalList().getItemsAmount(),
                        panelOwner.getHorizontalList().getItemSize(),
                        panelOwner.getHorizontalList().getPrototype().getDrawRegion().getMinX()
                )
        );
        panelOwner.getScrollBar().onPositionChanged();
    }

    public void showHint(int x, int y) {
        TextHint.show("CenteredElasticInertMotionListener", x, y);
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
package com.geargames.awtdemo.awt.components.forms.list.horz;

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
public class PButton_Horizontal_StubMotionListener extends PDummyRadioButton {

    private PPanel_HorizontalList panelOwner;

    public PButton_Horizontal_StubMotionListener(PPanel_HorizontalList panelOwner, PObject prototype) {
        super(prototype);
        this.panelOwner = panelOwner;
    }

    @Override
    public void onClick() {
        StubMotionListener motionListener = new StubMotionListener();
        motionListener.setScrollListener(panelOwner.getScrollBar());
        panelOwner.getHorizontalList().setMotionListener(
                //todo: adjustStubMotionListener только для Vertical?
                ScrollHelper.adjustStubMotionListener(
                        motionListener,
                        panelOwner.getHorizontalList().getDrawRegion(),
                        panelOwner.getHorizontalList().getShownItemsAmount(),
                        panelOwner.getHorizontalList().getItemSize(),
                        Graphics.LEFT
                )
        );
        panelOwner.getScrollBar().onPositionChanged();
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
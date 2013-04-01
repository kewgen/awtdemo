package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.list.horz.PPanel_HorizontalList;

/**
 * User: abarakov
 * Date: 11.02.13 11:45
 */
public class PButton_HorizontalList extends PDummyEntitledTouchButton {

    public PButton_HorizontalList() {
        super();
    }

    @Override
    public void onClick() {
        PPanel_HorizontalList panel = new PPanel_HorizontalList();
        panel.showModal();
    }
}
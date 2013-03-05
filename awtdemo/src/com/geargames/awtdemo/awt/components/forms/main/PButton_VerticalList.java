package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awtdemo.awt.components.common.PDummyEntitledTouchButton;
import com.geargames.awtdemo.awt.components.forms.list.vlist.PPanel_VerticalList;
import com.geargames.common.String;

/**
 * User: abarakov
 * Date: 19.02.13
 */
public class PButton_VerticalList extends PDummyEntitledTouchButton {

    public PButton_VerticalList(String caption) {
        super(caption);
    }

    public void onClick() {
        PPanel_VerticalList panel = new PPanel_VerticalList();
        panel.showModal();
    }
}
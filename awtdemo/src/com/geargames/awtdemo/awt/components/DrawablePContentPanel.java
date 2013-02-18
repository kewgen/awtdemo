package com.geargames.awtdemo.awt.components;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awt.components.PElement;
import com.geargames.common.packer.Index;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 11.02.13 18:00
 */
@Deprecated
public abstract class DrawablePContentPanel extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            createDefaultElementByIndex(index);
        }
    }

    PContentPanelImpl сontentPanel;

    public DrawablePContentPanel(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);
    }

    protected PContentPanelImpl getContentPanel()
    {
        return сontentPanel;
    }

    public void addChild(PElement element, int x, int y, boolean active) {
        сontentPanel.addChild(element, x, y, active);
    }

    public void addActiveChild(PElement element, int x, int y) {
        сontentPanel.addActiveChild(element, x, y);
    }

    public void addActiveChild(PElement element, Index index) {
        сontentPanel.addActiveChild(element, index);
    }

    public void addActiveChild(PElement element) {
        сontentPanel.addActiveChild(element);
    }

    public void addPassiveChild(PElement element, int x, int y) {
        сontentPanel.addPassiveChild(element, x, y);
    }

    public void addPassiveChild(PElement element, Index index) {
        сontentPanel.addPassiveChild(element, index);
    }

    public void addPassiveChild(PElement element) {
        сontentPanel.addPassiveChild(element);
    }
}
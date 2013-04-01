package com.geargames.awtdemo.awt.components.forms.menu;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awt.components.PElement;
import com.geargames.awtdemo.awt.components.common.PButtonClosePanel;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.ArrayList;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.awt.components.DrawablePPanel;

/**
 * User: abarakov
 * Date: 11.02.13
 */
public class PMenuExamPanel extends DrawablePPanel {

    private class PContentPanelImpl extends PContentPanel {
        private ArrayList closeButtonList;

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    PElement element = new PButtonClosePanel((PObject) index.getPrototype());
                    addActiveChild(element, index);
                    if (closeButtonList == null)
                        closeButtonList = new ArrayList();
                    closeButtonList.add(element);
                    break;
            }
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (parentPrototype.getIndexes().indexOf(index)) {
                // Игнорируем некоторые элементы формы пакера
                case 2:
                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
            }
        }

        public void setButtonParent(DrawablePPanel parent) {
            for (int i = 0; i < closeButtonList.size(); i++) {
                ((PButtonClosePanel) closeButtonList.get(i)).setParent(parent);
            }
        }
    }

    public PMenuExamPanel() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_MAIN_MENU));
    }

    private PMenuExamPanel(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);

        PContentPanelImpl сontentPanel = new PContentPanelImpl(prototype);
        сontentPanel.setButtonParent(this);
        setElement(сontentPanel);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
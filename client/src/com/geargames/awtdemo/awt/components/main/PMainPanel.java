package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.Anchors;
import com.geargames.common.Graphics;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.packer.PSprite;
import com.geargames.common.util.Region;
import com.geargames.awtdemo.app.Port;
import com.geargames.awtdemo.app.Application;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.awt.components.DrawablePPanel;

/**
 * User: abarakov
 * Date: 08.02.13
*/
// Posible names: PMainForm, PFMain
public class PMainPanel extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        protected void createSlotElementByIndex(IndexObject index) {
        }

        protected void createDefaultElementByIndex(IndexObject index) {
        }
    }

    private PSprite spriteBorder;
    PContentPanelImpl сontentPanel;

    public PMainPanel() {
        this(Application.getInstance().getRender().getObject(7)); // Объект задается только для определения размеров панели
    }

    public PMainPanel(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        spriteBorder = Application.getInstance().getRender().getSprite(28);

        Region region = getElement().getDrawRegion();
        region.setMinX(0);
        region.setMinY(0);
        region.setWidth(Port.getW());  // spriteBorder.getWidth();
        region.setHeight(Port.getH()); // spriteBorder.getHeight();

//----- Кнопки для открывания форм с демо компонентами -----------------------------------------------------------------

        PButton1 button1 = new PButton1(Application.getInstance().getRender().getObject(58)); // PScrollableComponentsPanel
        сontentPanel.addActiveChild(button1, 20, 40);

        PButton2 button2 = new PButton2(Application.getInstance().getRender().getObject(59));
        сontentPanel.addActiveChild(button2, 20, 90);

        PButtonMenuExam buttonMenuExam = new PButtonMenuExam(Application.getInstance().getRender().getObject(63));
        сontentPanel.addActiveChild(buttonMenuExam, 20, 140);
    }

    public void onHide() {
    }

    public void onShow() {
    }

    public void draw(Graphics graphics) {
        spriteBorder.draw(graphics, 0, 0);
        super.draw(graphics);
    }

}
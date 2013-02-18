package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PButton;
import com.geargames.awt.components.PElement;
import com.geargames.common.Graphics;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.packer.PSprite;
import com.geargames.common.util.Region;
import com.geargames.awtdemo.app.Port;
import com.geargames.awtdemo.app.Application;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.common.String;

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

        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
        }

        protected void createDefaultElementByIndex(IndexObject index) {
        }
    }

    private PSprite spriteBorder;
    PContentPanelImpl сontentPanel;

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 0, Y - 0);
    }

    public PMainPanel() {
        this(Application.getInstance().getRender().getObject(7)); // Прототип задается только для определения размеров панели
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

        PButton buttonButtons = new PButton_Buttons(String.valueOfC("КНОПКИ"));
        addChild(buttonButtons, 20, 20);

        PButton buttonText = new PButton_Text(String.valueOfC("ТЕКСТОВЫЕ"));
        addChild(buttonText, 180, 20);

        PButton buttonLists = new PButton_Lists(String.valueOfC("СПИСКИ"));
        addChild(buttonLists, 20, 70);

        PButton buttonMenuExam = new PButton_MenuExam(String.valueOfC("ОБРАЗЕЦ МЕНЮ"));
        addChild(buttonMenuExam, 180, 70);

        PButton buttonProgressbars = new PButton_Progressbars(String.valueOfC("PROGRESS BARS"));
        addChild(buttonProgressbars, 20, 120);

        PButton buttonExit = new PButton_Exit(String.valueOfC("ВЫХОД"));
        addChild(buttonExit, 20, 375);
    }

    public void onHide() {
    }

    public void onShow() {
    }

    public void draw(Graphics graphics) {
        spriteBorder.draw(graphics, this.getX(), this.getY());
        super.draw(graphics);
    }

}
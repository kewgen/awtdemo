package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PButton;
import com.geargames.awt.components.PElement;
import com.geargames.awtdemo.app.Graph;
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
        this(Application.getInstance().getRender().getObject(Graph.PAN_TYPE_BATTLE)); // Прототип задается только для определения размеров панели
    }

    public PMainPanel(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        spriteBorder = Application.getInstance().getRender().getSprite(Graph.SPR_PANEL_FRAME);

        Region region = getElement().getDrawRegion();
        region.setMinX(0);
        region.setMinY(0);
        region.setWidth(Port.getW());  // spriteBorder.getWidth();
        region.setHeight(Port.getH()); // spriteBorder.getHeight();

//----- Кнопки для открывания форм с демо компонентами -----------------------------------------------------------------

        PButton buttonButtons = new PButton_Buttons(String.valueOfC("BUTTONS"));
        addChild(buttonButtons, 20, 20);

        PButton buttonText = new PButton_Text(String.valueOfC("TEXTS"));
        addChild(buttonText, 180, 20);

        PButton buttonHorizontalList = new PButton_HorizontalList(String.valueOfC("HORZ LISTS"));
        addChild(buttonHorizontalList, 20, 70);

        PButton buttonVerticalList = new PButton_VerticalList(String.valueOfC("VERT LISTS"));
        addChild(buttonVerticalList, 180, 70);

        PButton buttonMenuExam = new PButton_MenuExam(String.valueOfC("MENU EXAMPLE"));
        addChild(buttonMenuExam, 20, 120);

        PButton buttonProgressbars = new PButton_Progressbars(String.valueOfC("PROGRESS BARS"));
        addChild(buttonProgressbars, 180, 120);

        PButton buttonSpinBoxes = new PButton_SpinBoxes(String.valueOfC("SPIN BOXES"));
        addChild(buttonSpinBoxes, 20, 170);

        PButton buttonSpinHints = new PButton_Hints(String.valueOfC("HINTS"));
        addChild(buttonSpinHints, 180, 170);

        PButton buttonExit = new PButton_Exit(String.valueOfC("EXIT"));
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
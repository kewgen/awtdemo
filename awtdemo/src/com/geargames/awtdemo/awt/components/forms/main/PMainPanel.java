package com.geargames.awtdemo.awt.components.forms.main;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PElement;
import com.geargames.awt.components.PEntitledTouchButton;
import com.geargames.awtdemo.application.Graph;
import com.geargames.common.Graphics;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.packer.PSprite;
import com.geargames.common.util.Region;
import com.geargames.awtdemo.application.Port;
import com.geargames.awtdemo.application.Application;
import com.geargames.awt.components.PContentPanel;
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

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
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

        PEntitledTouchButton buttonButtons = new PButton_Buttons();
        buttonButtons.setText("BUTTONS");
        addChild(buttonButtons, 20, 20);

        PEntitledTouchButton buttonText = new PButton_Text();
        buttonText.setText("TEXTS");
        addChild(buttonText, 180, 20);

        PEntitledTouchButton buttonHorizontalList = new PButton_HorizontalList();
        buttonHorizontalList.setText("HORZ LISTS");
        addChild(buttonHorizontalList, 20, 70);

        PEntitledTouchButton buttonVerticalList = new PButton_VerticalList();
        buttonVerticalList.setText("VERT LISTS");
        addChild(buttonVerticalList, 180, 70);

        PEntitledTouchButton buttonMenuExam = new PButton_MenuExam();
        buttonMenuExam.setText("MENU EXAMPLE");
        addChild(buttonMenuExam, 20, 120);

        PEntitledTouchButton buttonProgressbars = new PButton_Progressbars();
        buttonProgressbars.setText("PROGRESS BARS");
        addChild(buttonProgressbars, 180, 120);

        PEntitledTouchButton buttonSpinBoxes = new PButton_SpinBoxes();
        buttonSpinBoxes.setText("SPIN BOXES");
        addChild(buttonSpinBoxes, 20, 170);

        PEntitledTouchButton buttonSpinHints = new PButton_Hints();
        buttonSpinHints.setText("HINTS");
        addChild(buttonSpinHints, 180, 170);

        PEntitledTouchButton buttonExit = new PButton_Exit();
        buttonExit.setText("EXIT");
        addChild(buttonExit, 20, 375);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

    @Override
    public void draw(Graphics graphics) {
        spriteBorder.draw(graphics, this.getX(), this.getY());
        super.draw(graphics);
    }

}
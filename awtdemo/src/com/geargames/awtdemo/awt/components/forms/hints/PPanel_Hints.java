package com.geargames.awtdemo.awt.components.forms.hints;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 28.02.13
*/
public class PPanel_Hints extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        @Override
        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 109:
                    // Заголовок окна
                    PSimpleLabel caption = new PSimpleLabel(index);
                    caption.setText(String.valueOfC("HINTS"));
                    caption.setFont(PFontCollection.getFontFormTitle());
                    addPassiveChild(caption, index);
                    break;
            }
        }

        @Override
        protected void createDefaultElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (parentPrototype.getIndexes().indexOf(index)) {
                // Игнорируем некоторые элементы формы пакера
                case 1:
                case 3:
                    break;
                default:
                    super.createDefaultElementByIndex(index, parentPrototype);
            }
        }
    }

    PContentPanelImpl сontentPanel;
    PEntitledClosePanelButton buttonClose;

    public PPanel_Hints() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_Hints(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

        //----- Кнопки для отображения примеров всплывающих подсказок --------------------------------------------------

        PButton_ShowHint1 buttonHint1 = new PButton_ShowHint1();
        buttonHint1.setText(String.valueOfC("HINT 1"));
        addChild(buttonHint1, 20, 30);

        PButton_ShowHint2 buttonHint2 = new PButton_ShowHint2();
        buttonHint2.setText(String.valueOfC("HINT 2"));
        addChild(buttonHint2, 180, 30);

        PButton_ShowHint3 buttonHint3 = new PButton_ShowHint3();
        buttonHint3.setText(String.valueOfC("HINT 3"));
        addChild(buttonHint3, 20, 80);

        PButton_ShowHint4 buttonHint4 = new PButton_ShowHint4();
        buttonHint4.setText(String.valueOfC("HINT 4"));
        addChild(buttonHint4, 180, 80);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
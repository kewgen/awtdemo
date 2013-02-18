package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awt.components.PElement;
import com.geargames.awt.components.PSimpleLabel;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.Graphics;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.Region;

/**
 * User: abarakov
 * Date: 14.02.13
*/
public class PPanel_Text extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        protected void createSlotElementByIndex(IndexObject index, PObject parentPrototype) {
            switch (index.getSlot()) {
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 19:
                    // Заголовок окна
                    PSimpleLabel caption = new PSimpleLabel(index);
                    caption.setData(String.valueOfC("ТЕКСТОВЫЕ КОМПОНЕНТЫ"));
                    caption.setFont(PFontCollection.getFontFormTitle());
                    addActiveChild(caption, index);
                    break;
                default:
//                    super.createDefaultElementByIndex(index);
                    break;
            }
        }

        protected void createDefaultElementByIndex(IndexObject index) {
            switch (index.getSlot()) {
                case 0:
                case 2:
                    break;
                default:
                    super.createDefaultElementByIndex(index);
            }
        }
    }

    PContentPanelImpl сontentPanel;
    PEntitledClosePanelButton buttonClose;

    public PPanel_Text() {
        this(Application.getInstance().getRender().getObject(12));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_Text(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

//----- Компонент "Текстовая надпись" (Текстовая метка) ----------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();                       // Создание компонента
        label1.setData(String.valueOfC("Label1"));                      // Задаем текст компонента
        addChild(label1, 20, 30); // TODO : Почему 30 вместо 20?

////----- Компонент "Текстовая область" ----------------------------------------------------------------------------------

        HintedTextArea textArea = new HintedTextArea();                 // Создание компонента
        Region regionTextArea = textArea.getDrawRegion();               // Размеры компонента
        regionTextArea.setWidth(160);                                   // Ширина компонента
        regionTextArea.setHeight(80);                                   // Высота компонента
        textArea.setData(String.valueOfC("Line1\n" +                    // Задаем текст компонента
                "Line2 Long\n" +
                "Line3\n" +
                "Line4\n" +
                "Line5\n" +
                "Line6"));
//        textArea.setFont(Application.getInstance().getFont10());        // Шрифт используемый при отображении
        textArea.setColor(0x0000FF);                                    // Цвет текста и рамки компонента (синий)
        textArea.setFormat(Graphics.LEFT | Graphics.TOP);               // Выравнивание по горизонтали и вертикали
        textArea.setEllipsis(false);                                    //
        textArea.setX(0);                                               // Позиция компонента по горизонтали относительно родителя
        textArea.setY(0);                                               // Позиция компонента по вертикали относительно родителя
        textArea.setRawHeight(14);                                      // Высота одной строки в многострочном тексте

        addChild(textArea, 80, 20);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
package com.geargames.awtdemo.awt.components.forms.texts;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.*;
import com.geargames.awtdemo.application.Application;
import com.geargames.awtdemo.application.Graph;
import com.geargames.awtdemo.application.PFontCollection;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.Graphics;
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
                    caption.setText("TEXTS");
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

    public PPanel_Text() {
        this(Application.getInstance().getRender().getObject(Graph.PAN_BATTLE_CREATE));
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

        PSimpleLabel label1 = new PSimpleLabel();                        // Создание компонента
        label1.setText("LABEL WITH SYSTEM FONT");                        // Задаем текст компонента
        addChild(label1, 20, 30); // TODO : Почему 30 вместо 20?

        PSimpleLabel label2 = new PSimpleLabel();                        // Создание компонента
        label2.setText("LABEL WITH RASTER FONT");                        // Задаем текст компонента
        label2.setFont(PFontCollection.getFontLabel());                  // Задаем растровый шрифт
        addChild(label2, 20, 50); // TODO : Почему 50 вместо 40?

//----- Компонент "Текстовая область" с системным шрифтом --------------------------------------------------------------

        TextArea textArea1 = new TextArea();                             // Создание компонента
        Region regionTextArea1 = textArea1.getDrawRegion();              // Размеры компонента
        regionTextArea1.setWidth(160);                                   // Ширина компонента
        regionTextArea1.setHeight(80);                                   // Высота компонента
        textArea1.setText(                                               // Задаем текст компонента
                "\n" +
                "\n" +
                "\n" +
                "Line1\n" +
                "Line2 Long\n" +
                "\n" +
                "\n" +
                "Line3 Long Long Long\n" +
                "Line4 Long Long Long Long Long Long\n" +
                "Line5\n" +
                "Line6" +
                "\n" +
                "\n");
        textArea1.setFont(null);                                         // Системный шрифт будет использоваться при отображении
        textArea1.setColor(0xffffff);                                    // Цвет текста и рамки компонента (синий)
        textArea1.setFormat(Graphics.LEFT);                              // Выравнивание текста по горизонтали
        textArea1.setEllipsis(false);                                    //
        textArea1.setX(0);                                               // Позиция компонента по горизонтали относительно родителя
        textArea1.setY(0);                                               // Позиция компонента по вертикали относительно родителя
        textArea1.setRawHeight(14);                                      // Высота одной строки в многострочном тексте

        addChild(textArea1, 20, 70);

//----- Компонент "Текстовая область" с растровым шрифтом --------------------------------------------------------------

        TextArea textArea2 = new TextArea();                             // Создание компонента
        Region regionTextArea2 = textArea2.getDrawRegion();              // Размеры компонента
        regionTextArea2.setWidth(160);                                   // Ширина компонента
        regionTextArea2.setHeight(80);                                   // Высота компонента
        textArea2.setText(                                               // Задаем текст компонента
                "LINE1\n" +
                "LINE2 LONG\n" +
                "LINE3 LONG LONG LONG\n" +
                "LINE4 LONG LONGLONG LONG LONGLONGLONG LONG LONG LONG LONG LONGLONGLONGLONG LONGLONG LONGLONGLONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG LONG\n" +
                "LINE5\n" +
                "LINE6");
        textArea2.setFont(PFontCollection.getFont10());                  // Растровый шрифт будет использоваться при отображении
        textArea2.setColor(0xffffff);                                    // Цвет текста и рамки компонента (синий)
        textArea2.setFormat(Graphics.LEFT);                              // Выравнивание текста по горизонтали
        textArea2.setEllipsis(false);                                    //
        textArea2.setX(0);                                               // Позиция компонента по горизонтали относительно родителя
        textArea2.setY(0);                                               // Позиция компонента по вертикали относительно родителя
        textArea2.setRawHeight(14);                                      // Высота одной строки в многострочном тексте

        addChild(textArea2, 200, 70);

        // Кнопка-переключатель возможности скроллить список с помощью касаний
        PToggleButton buttonStuck = new PButton_ToggleStuck(
                new ScrollableArea[] {textArea1, textArea2}, Application.getInstance().getRender().getObject(61));
        addChild(buttonStuck, 20, 160);

        // Кнопка-переключатель режима отсечения крайних элементов списка
        PToggleButton buttonStrictlyClipped = new PButton_ToggleStrictlyClipped(
                new ScrollableArea[] {textArea1, textArea2}, Application.getInstance().getRender().getObject(62));
        addChild(buttonStrictlyClipped, 73, 160);

        // Кнопка-переключатель функции "обрезки" текста, не помещающегося в пределах области отрисовки
        PToggleButton buttonEllipsis = new PButton_ToggleEllipsis(
                new TextArea[] {textArea1, textArea2}, Application.getInstance().getRender().getObject(63));
        addChild(buttonEllipsis, 126, 160);

        // Кнопка-переключатель цвета
        PEntitledTouchButton buttonColor = new PButton_ToggleColor(
                new TextArea[] {textArea1, textArea2});
        buttonColor.setText("COLOR");
        addChild(buttonColor, 20, 210);

        // Кнопка-переключатель горизонтального выравнивания
        PEntitledTouchButton buttonFormat = new PButton_ToggleFormat(
                new TextArea[] {textArea1, textArea2});
        buttonFormat.setText("FORMAT");
        addChild(buttonFormat, 175, 210);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

}
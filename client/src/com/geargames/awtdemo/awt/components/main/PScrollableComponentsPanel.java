package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awt.components.PElement;
import com.geargames.awt.components.PSimpleLabel;
import com.geargames.common.Graphics;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.Region;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.awt.components.DrawablePPanel;

/**
 * User: abarakov
 * Date: 08.02.13
*/
public class PScrollableComponentsPanel extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
//            getDrawRegion().setMinX(0);
//            getDrawRegion().setMinY(0);
        }

        protected void createSlotElementByIndex(IndexObject index) {
            // Никаких слотовых элементов из прототипа не нужно отображать
            super.createDefaultElementByIndex(index);
        }

        protected void createDefaultElementByIndex(IndexObject index) {
            super.createDefaultElementByIndex(index);
        }
    }

//    private PSprite spriteBorder;
    PContentPanelImpl сontentPanel;

    public PScrollableComponentsPanel() {
        this(Application.getInstance().getRender().getObject(9));
    }

    private void addActiveChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 170, Y - 106);
    }

    public PScrollableComponentsPanel(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

//        Region region = getElement().getDrawRegion();
//        region.setMinX(0);
//        region.setMinY(0);
//        region.setWidth(Port.getW());
//        region.setHeight(Port.getH());

//----- Компонент "Текстовая надпись" (Текстовая метка) ----------------------------------------------------------------

        PSimpleLabel label1 = new PSimpleLabel();                       // Создание компонента
        label1.setData(String.valueOfC("Label1"));                      // Задаем текст компонента
        addActiveChild(label1, 20, 30); // TODO : Почему 30 вместо 20?

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

        addActiveChild(textArea, 80, 20);

//----- Компонент "Текстовая подсказка" --------------------------------------------------------------------------------

//        DrawablePPanel panelHint = new DefaultDrawablePPanel();         // Создание панельки верхнего уровня
//        show(panelHint);                                                // Делаем панель видимой
//        TextHint.setDrawablePanel(panelHint);

//        TextHint textHint = TextHint.getInstance();
//        textHint.setSkinObject(render.getFrame(675), render, 16, 24, 16, 24);
//        TextHint.show(String.valueOfC("Line1\n" +
//                "Line2 Long\n" +
//                "Line3\n" +
//                "Line4\n" +
//                "Line5\n" +
//                "Line6"), 260, 20);

//----- Компонент "Горизонтальный список" ------------------------------------------------------------------------------

//        HorizontalList horizontalList = new HorizontalList();                             // Создание компонента
//        Region region = horizontalList.getDrawRegion();                       // Размеры компонента
//        region.setWidth(160);                                           // Ширина компонента
//        region.setHeight(80);                                           // Высота компонента
//        horizontalList.setData(String.valueOfC("Line1\nLine2 Long\nLine3"));  // Задаем текст компонента
////        horizontalList.setFont(Application.getInstance().getFont10());        // Шрифт используемый при отображении
//        horizontalList.setColor(0x0000FF);                                    // Цвет текста и рамки компонента (синий)
//        horizontalList.setFormat(Graphics.LEFT | Graphics.TOP);               // Выравнивание по горизонтали и вертикали
//        horizontalList.setEllipsis(false);                                    //
//        horizontalList.setX(0);                                               // Позиция компонента по горизонтали относительно родителя
//        horizontalList.setY(0);                                               // Позиция компонента по вертикали относительно родителя
//        horizontalList.setRawHeight(14);                                      // Высота одной строки в многострочном тексте
//
//        DrawablePPanel panel3 = new DefaultDrawablePPanel();            // Создание панельки верхнего уровня
//        panel3.setElement(horizontalList);                              // Добавление компонента на панель
//        panel3.setX(380);                                               // Позиция панельки по горизонтали относительно окна приложения
//        panel3.setY(20);                                                // Позиция панельки по вертикали относительно окна приложения
//        show(panel3);                                                   // Делаем панель видимой

//----------------------------------------------------------------------------------------------------------------------

        PClosePanelButton button1 = new PClosePanelButton();
        button1.setParent(this);
        addActiveChild(button1, 20, 140);
    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

    @Override
    public void draw(Graphics graphics) {
//        spriteBorder.draw(graphics, 0, 0);
        super.draw(graphics);
    }

}
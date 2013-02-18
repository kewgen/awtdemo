package com.geargames.awtdemo.awt.components.forms.lists2;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PContentPanel;
import com.geargames.awt.components.PElement;
import com.geargames.awt.components.PSimpleLabel;
import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.PFontCollection;
import com.geargames.awtdemo.app.Render;
import com.geargames.awtdemo.awt.components.DrawablePPanel;
import com.geargames.awtdemo.awt.components.common.PEntitledClosePanelButton;
import com.geargames.common.Graphics;
import com.geargames.common.String;
import com.geargames.common.packer.IndexObject;
import com.geargames.common.packer.PObject;

/**
 * User: abarakov
 * Date: 08.02.13
*/
//PScrollableComponentsPanel
public class PPanel_Lists extends DrawablePPanel {

    protected class PContentPanelImpl extends PContentPanel {

        public PContentPanelImpl(PObject prototype) {
            super(prototype);
        }

        protected void createSlotElementByIndex(IndexObject index) {
            switch (index.getSlot()) {
                case 13:
                    // Кнопка закрытия окна
                    buttonClose = new PEntitledClosePanelButton((PObject) index.getPrototype());
                    addActiveChild(buttonClose, index);
                    break;
                case 19:
                    // Заголовок окна
                    PSimpleLabel caption = new PSimpleLabel(index);
                    caption.setFont(PFontCollection.getFontFormTitle());
                    caption.setData(String.valueOfC("СПИСКИ"));
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

    public PPanel_Lists() {
        this(Application.getInstance().getRender().getObject(12));
    }

    private void addChild(PElement element, int X, int Y) {
        сontentPanel.addActiveChild(element, X - 227, Y - 163);
    }

    public PPanel_Lists(PObject prototype) {
        super();
        setAnchor(Anchors.CENTER_ANCHOR);
        сontentPanel = new PContentPanelImpl(prototype);
        setElement(сontentPanel);

        buttonClose.setParent(this);

//----- Компонент "Горизонтальный список" ------------------------------------------------------------------------------

        FrameCollection frames = new FrameCollection();
        Render render = Application.getInstance().getRender();
        frames.add(render.getFrame(596));
        frames.add(render.getFrame(593));
        frames.add(render.getFrame(595));
        frames.add(render.getFrame(596));
        frames.add(render.getFrame(596));
        frames.add(render.getFrame(596));
//        frames.add(render.getFrame(420));
//        frames.add(render.getFrame(421));
//        frames.add(render.getFrame(422));
//        frames.add(render.getFrame(423));
//        frames.add(render.getFrame(424));
//        frames.add(render.getFrame(425));
//        frames.add(render.getFrame(426));
//        frames.add(render.getFrame(427));

        PObject face = Application.getInstance().getRender().getObject(23);

        HorizontalList horizontalList = new HorizontalList(frames, face);
//        horizontalList.se

        addChild(horizontalList, 227, 24);

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

    }

    @Override
    public void onHide() {
    }

    @Override
    public void onShow() {
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
    }

}
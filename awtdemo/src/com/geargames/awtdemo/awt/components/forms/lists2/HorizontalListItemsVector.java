package com.geargames.awtdemo.awt.components.forms.lists2;

import com.geargames.awtdemo.app.Application;
import com.geargames.awtdemo.app.Render;
import com.geargames.common.packer.PFrame;

import java.util.Vector;

/**
 * User: abarakov
 * Реализация вектора элементов HorizontalListItem.
 * Реализация не поддерживает изменение содержимого вектора.
 */
public class HorizontalListItemsVector extends Vector {
//    private Render render;
    private PFrame value;
    private HorizontalListItem item;
    FrameCollection collection;
//
    public HorizontalListItemsVector(FrameCollection collection, /*Render render, */HorizontalListItem item) {
        super(0);
        this.collection = collection;
        this.item = item;
//        this.render = render;
    }

    public Object elementAt(int index) {
        Render render = Application.getInstance().getRender();
        PFrame tmp = (PFrame) collection.get(index);
        if(tmp != value) {
            item.setPrototype(tmp);
            value = tmp;
            item.setValue(value);
        }
        return item;
    }

    public int size() {
        return collection.size();
    }
}

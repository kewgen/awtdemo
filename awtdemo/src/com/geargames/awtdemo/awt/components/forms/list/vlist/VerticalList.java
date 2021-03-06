package com.geargames.awtdemo.awt.components.forms.list.vlist;

import com.geargames.awt.components.PPrototypeElement;
import com.geargames.awt.components.PVerticalScrollView;
import com.geargames.awt.utils.motions.ElasticInertMotionListener;
import com.geargames.common.Graphics;
import com.geargames.common.packer.PObject;

import java.util.Vector;

/**
 * User: abarakov
 * Вертикальный список для прокручивания элементов.
 */
public class VerticalList extends PVerticalScrollView {
    private Vector items;

    public VerticalList(int collectionSize, PObject listPrototype) {
        super(listPrototype);

        items = new Vector(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            PPrototypeElement item = new PPrototypeElement();
            item.setPrototype(getPrototype().getPrototype());
            items.add(item);
        }
    }

    public void initiateMotionListener() {
        ElasticInertMotionListener motionListener = new ElasticInertMotionListener();
        setMotionListener(motionListener);
    }

    @Override
    public void initiate(Graphics graphics) {
        initiateMotionListener();
        setInitiated(true);
    }

    @Override
    public Vector getItems() {
        return items;
    }

}
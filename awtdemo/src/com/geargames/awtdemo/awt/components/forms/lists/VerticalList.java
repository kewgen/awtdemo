package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.components.PPrototypeElement;
import com.geargames.awt.components.PVerticalScrollView;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.awt.utils.motions.ElasticInertMotionListener;
import com.geargames.common.Graphics;
import com.geargames.common.packer.Index;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.ArrayList;

import java.util.Vector;

/**
 * User: abarakov
 * Вертикальный список для прокручивания элементов.
 */
public class VerticalList extends PVerticalScrollView {
    private Vector items;

    public boolean isVisible() {
        return true;
    }

    public VerticalList(int collectionSize, PObject listPrototype) {
        super(listPrototype);

        items = new Vector(collectionSize);
        for (int i=0; i<collectionSize; i++) {
            PPrototypeElement item = new PPrototypeElement();
            item.setPrototype(getPrototype().getPrototype());
            items.add(item);
        }
    }

    public void initiateMotionListener() {
        ElasticInertMotionListener motionListener = new ElasticInertMotionListener();
        setMotionListener(motionListener);
    }

    public void initiate(Graphics graphics) {
        initiateMotionListener();
        setInitiated(true);
    }

    public Vector getItems() {
        return items;
    }
}
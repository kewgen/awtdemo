package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.components.PHorizontalScrollView;
import com.geargames.common.packer.Index;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.ArrayList;

import java.util.Vector;

/**
 * User: abarakov
 * Горизонтальный список для прокручивания элементов.
 */
public class HorizontalList extends PHorizontalScrollView {
    private Vector items;

    public HorizontalList(ArrayList collection, PObject listPrototype) {
        super(listPrototype);

        Index frameFaceIndex = listPrototype.getIndexBySlot(0);
        PObject faceObject = (PObject) frameFaceIndex.getPrototype();

        items = new Vector(collection.size());
        for (int i=0; i<collection.size(); i++) {
            PFrame frame = (PFrame) collection.get(i);
            HorizontalListItem item = new HorizontalListItem(faceObject);
            item.setRegion(getPrototype().getDrawRegion());
            item.setValue(frame);
            items.add(item);
        }
    }

    @Override
    public Vector getItems() {
        return items;
    }

}

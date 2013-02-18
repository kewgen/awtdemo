package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.components.HorizontalScrollView;
import com.geargames.awt.components.PPrototypeElement;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.*;
import com.geargames.common.Graphics;
import com.geargames.common.packer.Index;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.ArrayList;
import com.geargames.common.util.Region;

import java.util.Vector;

/**
 * User: abarakov
 * Горизонтальное меню для прокручивания кнопок.
 */
public class HorizontalList extends HorizontalScrollView {
    private Vector items;
//    private int position;
    private Region region;
    private Region prototypeRegion;
//
    public Region getDrawRegion() {
        return region;
    }

    public Region getTouchRegion() {
        return region;
    }

    public boolean isVisible() {
        return true;
    }

    public HorizontalList(ArrayList collection, PObject listPrototype) {
        setMargin(0);
        setStuck(false);
        setStrictlyClipped(false);
//        this.position = 0;
        Index frameListIndex = listPrototype.getIndexBySlot(110);
        PFrame frameList = (PFrame) frameListIndex.getPrototype();

        region = new Region();
        region.setMinX(frameListIndex.getX());
        region.setMinY(frameListIndex.getY());
        region.setWidth(frameList.getWidth());
        region.setHeight(90/*frameList.getHeight()*/);

        Index frameFaceIndex = listPrototype.getIndexBySlot(0);
        PObject faceObject = (PObject) frameFaceIndex.getPrototype();
        PFrame frameFace = (PFrame) faceObject.getIndexBySlot(110).getPrototype();

        prototypeRegion = new Region();
        prototypeRegion.setMinX(frameFaceIndex.getX());
        prototypeRegion.setMinY(frameFaceIndex.getY());
        prototypeRegion.setWidth(frameFace.getWidth());
        prototypeRegion.setHeight(frameFace.getHeight());

        items = new Vector(collection.size());
        for (int i=0; i<collection.size(); i++) {
            PFrame frame = (PFrame) collection.get(i);
            HorizontalListItem item = new HorizontalListItem(faceObject);
            item.setRegion(prototypeRegion);
            item.setValue(frame);
            items.add(item);
        }
    }

    public void initiateMotionListener() {
        CenteredElasticInertMotionListener motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        setMotionListener(ScrollHelper.adjustHorizontalCenteredMenuMotionListener(
                motionListener, getDrawRegion(), getItemsAmount(), getItemSize(), getPrototype().getDrawRegion().getMinX()));

//        InertMotionListener motionListener = new InertMotionListener();
//        setMotionListener(ScrollHelper.adjustHorizontalInertMotionListener(
//                motionListener, getDrawRegion(), getShownItemsAmount(), getItemSize()));

//        ElasticInertMotionListener motionListener = new ElasticInertMotionListener();
//        setMotionListener(motionListener);
    }

    public void initiate(Graphics graphics) {
        initiateMotionListener();
        setInitiated(true);
    }

//    public boolean hasNext() {
//        return position + 1 < items.size();
//    }
//
//    public boolean hasPrevious() {
//        return position - 1 >= 0;
//    }
//
//    /**
//     * Отцентровать следующий элемент.
//     */
//    public void next() {
//        if (hasNext()) {
//            getMotionListener().onClick(++position);
//        }
//    }
//
//    /**
//     * Отцентровать предыдущий элемент.
//     */
//    public void previous() {
//        if (hasPrevious()) {
//            getMotionListener().onClick(--position);
//        }
//    }

    public Vector getItems() {
        return items;
    }

    public PPrototypeElement getPrototype() {
        return (PPrototypeElement)items.get(0);
    }
}

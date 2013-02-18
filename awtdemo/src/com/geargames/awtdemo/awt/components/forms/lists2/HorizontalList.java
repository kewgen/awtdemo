package com.geargames.awtdemo.awt.components.forms.lists2;

import com.geargames.awt.components.HorizontalScrollView;
import com.geargames.awt.components.PPrototypeElement;
import com.geargames.awt.utils.ScrollHelper;
import com.geargames.awt.utils.motions.CenteredElasticInertMotionListener;
import com.geargames.common.Graphics;
import com.geargames.common.packer.Index;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.PObject;
import com.geargames.common.util.Region;

import java.util.Vector;

/**
 * User: abarakov
 * Горизонтальное меню для прокручивания кнопок.
 */
public class HorizontalList extends HorizontalScrollView {
    private HorizontalListItemsVector items;
    private HorizontalListItem prototype;
    private int position;
    private CenteredElasticInertMotionListener motionListener;
//
    private Region region;
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

    public HorizontalList(FrameCollection collection, PObject face) {
        setMargin(0);
        setStuck(true);
        setStrictlyClipped(false);
        this.position = 0;
        Index frameIndex = face.getIndexBySlot(110);
        PFrame frame = (PFrame) frameIndex.getPrototype();

        prototype = new HorizontalListItem(frame);
        region = new Region();

        region.setWidth(frame.getWidth());
        region.setHeight(frame.getHeight());
        region.setMinX(frameIndex.getX());
        region.setMinY(frameIndex.getY());
        prototype.setRegion(region);

        items = new HorizontalListItemsVector(collection, /*Application.getInstance().getRender(), */prototype);

        motionListener = new CenteredElasticInertMotionListener();
        motionListener.setInstinctPosition(false);
        setMotionListener(motionListener);
    }

    public void initiateMotionListener() {
        ScrollHelper.adjustHorizontalCenteredMenuMotionListener(motionListener,
                getDrawRegion(), getItemsAmount(), getItemSize(), region.getMinX());
    }

    public void initiate(Graphics graphics) {
        initiateMotionListener();
        setInitiated(true);
    }

    public boolean hasNext() {
        return position + 1 < items.size();
    }

    public boolean hasPrevious() {
        return position - 1 >= 0;
    }

    /**
     * Отцентровать следующий элемент.
     */
    public void next() {
        if (hasNext()) {
            getMotionListener().onClick(++position);
        }
    }

    /**
     * Отцентровать предыдущий элемент.
     */
    public void previous() {
        if (hasPrevious()) {
            getMotionListener().onClick(--position);
        }
    }

    /**
     * Вернуть текущего бойца.
     *
     * @return
     */
//    public Warrior getWarrior() {
//        return ((HorizontalListItem) items.elementAt(position)).getWarrior();
//    }

    public Vector getItems() {
        return items;
    }

    public PPrototypeElement getPrototype() {
        return prototype;
    }
}

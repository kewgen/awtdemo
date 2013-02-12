package com.geargames.awtdemo.awt.components.main;

/**
 * User: mikhail v. kutuzov
 * Горизонтальное меню для прокручивания лиц бойцов.
 */
public class HorizontalList { //extends HorizontalScrollView {
//    private HorizontalListItemsVector items;
//    private HorizontalListItem prototype;
//    private int position;
//    private CenteredElasticInertMotionListener motionListener;
//
//    private Region region;
//
//    public Region getDrawRegion() {
//        return region;
//    }
//
//    public Region getTouchRegion() {
//        return region;
//    }
//
//    public boolean isVisible() {
//        return true;
//    }
//
//    public HorizontalList(WarriorCollection warriors, PObject face, Render render) {
//        setMargin(0);
//        setStuck(true);
//        setStrictlyClipped(false);
//        this.position = 0;
//        Index frameIndex = face.getIndexBySlot(110);
//        PFrame frame = (PFrame) frameIndex.getPrototype();
//
//        prototype = new HorizontalListItem();
//        region = new Region();
//
//        region.setWidth(frame.getWidth());
//        region.setHeight(frame.getHeight());
//        region.setMinX(frameIndex.getX());
//        region.setMinY(frameIndex.getY());
//        prototype.setRegion(region);
//
//        items = new HorizontalListItemsVector(warriors, render, prototype);
//
//        motionListener = new CenteredElasticInertMotionListener();
//        motionListener.setInstinctPosition(false);
//        setMotionListener(motionListener);
//    }
//
//    public void initiateMotionListener() {
//        ScrollHelper.adjustHorizontalCenteredMenuMotionListener(motionListener,
//                getDrawRegion(), getItemsAmount(), getItemSize(), region.getMinX());
//    }
//
//    public void initiate(Graphics graphics) {
//        initiateMotionListener();
//        setInitiated(true);
//    }
//
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
//
//    /**
//     * Вернуть текущего бойца.
//     *
//     * @return
//     */
////    public Warrior getWarrior() {
////        return ((HorizontalListItem) items.elementAt(position)).getWarrior();
////    }
//
//    public Vector getItems() {
//        return items;
//    }
//
//    public PPrototypeElement getPrototype() {
//        return prototype;
//    }
}

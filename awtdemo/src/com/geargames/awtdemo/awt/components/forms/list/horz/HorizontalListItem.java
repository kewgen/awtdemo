package com.geargames.awtdemo.awt.components.forms.list.horz;

import com.geargames.awt.components.PPrototypeElement;
import com.geargames.common.Graphics;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.Prototype;
import com.geargames.common.util.NullRegion;

/**
 * User: abarakov
 * Элемент горизонтального списка.
 */
public class HorizontalListItem extends PPrototypeElement { // PTouchButton {
//    private Region region;
    private PFrame value;

    public HorizontalListItem(Prototype prototype) {
//        super(prototype);
        setPrototype(prototype);
        setRegion(NullRegion.instance);
    }

//    @Override
//    public boolean onEvent(int code, int param, int x, int y) {
//        return false;
//    }
//
//    @Override
//    public Region getTouchRegion() {
//        return region;
//    }
//
//    @Override
//    public Region getDrawRegion() {
//        return region;
//    }

//    @Override
//    public void setRegion(Region drawRegion) {
//        this.region = drawRegion;
//    }
//
    public PFrame getValue() {
        return value;
    }

    public void setValue(PFrame value) {
        this.value = value;
    }

    @Override
    public void draw(Graphics graphics, int x, int y) {
//        graphics.drawString(value, x, y, Anchors.CENTER_ANCHOR);
        value.draw(graphics, x, y);
    }
//
//    @Override
//    public void onClick() {
//
//    }

}

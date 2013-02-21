package com.geargames.awtdemo.awt.components.forms.lists;

import com.geargames.awt.components.PPrototypeElement;
import com.geargames.common.Graphics;
import com.geargames.common.packer.PFrame;
import com.geargames.common.packer.Prototype;
import com.geargames.common.util.NullRegion;

/**
 * User: abarakov
 * Элемент вертикального списка.
 */
@Deprecated
public class VerticalListItem extends PPrototypeElement { // PTouchButton {
//    private Region region;
    private PFrame value;

    public VerticalListItem(Prototype prototype) {
//        super(prototype);
        setPrototype(prototype);
        setRegion(NullRegion.instance);
    }

//    public boolean event(int code, int param, int x, int y) {
//        return false;
//    }
//
//    public Region getTouchRegion() {
//        return region;
//    }
//
//    public Region getDrawRegion() {
//        return region;
//    }

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

    public void draw(Graphics graphics, int x, int y) {
        value.draw(graphics, x, y);
    }
//
//    public boolean isVisible() {
//        return true;
//    }
//
//    public void action() {
//
//    }

}

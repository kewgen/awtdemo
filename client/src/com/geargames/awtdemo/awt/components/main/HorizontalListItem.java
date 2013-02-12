package com.geargames.awtdemo.awt.components.main;

import com.geargames.awt.Anchors;
import com.geargames.awt.components.PElement;
import com.geargames.common.Graphics;
import com.geargames.common.util.Region;

/**
 * User: mikhail v. kutuzov
 * Элемент горизонтального списка.
 */
public class HorizontalListItem extends PElement { //PPrototypeElement {
    private Region region;
    private String value;

    public HorizontalListItem() {
    }

    public boolean event(int code, int param, int x, int y) {
        return false;
    }

    public Region getTouchRegion() {
        return region;
    }

    public Region getDrawRegion() {
        return region;
    }

    public void setRegion(Region drawRegion) {
        this.region = drawRegion;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void draw(Graphics graphics, int x, int y) {
        graphics.drawString(com.geargames.common.String.valueOfC(value), x, y, Anchors.CENTER_ANCHOR);
    }

    public boolean isVisible() {
        return true;
    }
}

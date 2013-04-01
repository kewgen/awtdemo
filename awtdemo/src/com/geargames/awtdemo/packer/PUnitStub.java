package com.geargames.awtdemo.packer;

import com.geargames.common.Graphics;
import com.geargames.common.packer.PUnit;

/**
 * User: mikhail v. kutuzov
 * Затычка для юнита.
 */
public class PUnitStub extends PUnit {

    public PUnitStub(int prototypesCount) {
        super(prototypesCount);
    }

    @Override
    public void draw(Graphics graphics, int x, int y, Object unit) {
    }
}

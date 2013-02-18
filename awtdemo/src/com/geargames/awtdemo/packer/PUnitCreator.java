package com.geargames.awtdemo.packer;

import com.geargames.common.packer.PCreator;
import com.geargames.common.packer.PUnit;
import com.geargames.awtdemo.packer.PUnitStub;

/**
 * User: mikhail v. kutuzov
 */
public class PUnitCreator extends PCreator {

    public PUnit createUnit(int pid, int size) {
        return new PUnitStub(size);
    }
}

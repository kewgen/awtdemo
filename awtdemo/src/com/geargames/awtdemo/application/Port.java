package com.geargames.awtdemo.application;

import com.geargames.platform.PortPlatform;

/**
 * Порт проекта
 */
public class Port extends com.geargames.common.Port {

    public static void init() {
        setWH(724, 442);
        PortPlatform.init();
    }

}

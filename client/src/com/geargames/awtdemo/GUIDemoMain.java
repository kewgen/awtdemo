package com.geargames.awtdemo;

import com.geargames.awtdemo.app.MIDlet;
import com.geargames.awtdemo.app.Port;

import java.io.IOException;

/**
 * User: abarakov
 * Date: 02.02.12 14:23
 */

public class GUIDemoMain extends com.geargames.Main {

     public static void main(String[] args) throws IOException {
        LOG_FILE_NAME = "awtdemo";
        GUIDemoMain main = new GUIDemoMain();
        main.commonMain(args);
        Port.init();
        MIDlet project = new MIDlet();
        project.startApp();
    }

}
package com.geargames.awtdemo;

import com.geargames.awtdemo.application.MIDlet;
import com.geargames.awtdemo.application.Port;
import java.io.IOException;

/**
 * User: abarakov
 * Date: 02.02.12 14:23
 */

public class AWTDemoMain extends com.geargames.Main {

    public static void main(String[] args) throws IOException {
        LOG_FILE_NAME = "awtdemo";
        AWTDemoMain main = new AWTDemoMain();
        main.commonMain(args);
        Port.init();
        MIDlet project = new MIDlet();
        project.startApp();
    }

}
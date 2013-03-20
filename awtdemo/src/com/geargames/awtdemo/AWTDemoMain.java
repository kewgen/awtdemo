package com.geargames.awtdemo;

import com.geargames.awtdemo.application.MIDlet;
import com.geargames.awtdemo.application.Port;
import com.geargames.common.logging.Logger;
import com.geargames.common.util.Recorder;

import java.io.IOException;

/**
 * User: abarakov
 * Date: 02.02.12 14:23
 */
public class AWTDemoMain extends com.geargames.platform.Main {

    public static void main(String[] args) throws IOException {
        Logger.logFileName         = "awtdemo";
        Recorder.storageFolder     = "data.storage";
        Recorder.storageProperties = "property.storage";

        AWTDemoMain main = new AWTDemoMain();
        main.commonMain(args);
        Port.init();
        MIDlet project = new MIDlet();
        project.startApp();
    }

}
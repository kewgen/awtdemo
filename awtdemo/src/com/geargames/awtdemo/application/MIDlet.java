package com.geargames.awtdemo.application;

import com.geargames.common.String;
import com.geargames.common.logging.Debug;

public final class MIDlet extends com.geargames.platform.MIDlet {

    public MIDlet() {
        super();
        setLocationRelativeTo(null);
    }

    public void startApp() {
        Manager manager = (Manager)getManager();
        try {
            if (manager == null) {
                manager = Manager.getInstance(this);
            }
            manager.startApp();
        } catch (Exception e) {
            Debug.error(String.valueOfC(""), e);
        }
    }

    protected void onResume() {
    }

    public void onPause() {
        Manager manager = (Manager)getManager();
        if (manager != null) {
            manager.pauseApp();
        }
    }

    public void destroyApp(boolean b) {
        try {

        } catch (Exception e) {
            Debug.error(String.valueOfC(""), e);
        }

    }

    @Override
    protected com.geargames.platform.Manager getManager() {
        return Manager.getInstance();
    }
}
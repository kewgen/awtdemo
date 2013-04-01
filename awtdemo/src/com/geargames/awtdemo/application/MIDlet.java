package com.geargames.awtdemo.application;

import com.geargames.common.logging.Debug;

public final class MIDlet extends com.geargames.platform.MIDlet {

    public MIDlet() {
        super();
        setLocationRelativeTo(null);
    }

    @Override
    public void startApp() {
        Manager manager = (Manager)getManager();
        try {
            if (manager == null) {
                manager = Manager.getInstance(this);
            }
            manager.startApp();
        } catch (Exception e) {
            Debug.error("", e);
        }
    }

    @Override
    protected com.geargames.platform.Manager getManager() {
        return Manager.getInstance();
    }
}
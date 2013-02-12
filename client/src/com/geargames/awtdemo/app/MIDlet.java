package com.geargames.awtdemo.app;

import com.geargames.Debug;

public final class MIDlet extends com.geargames.MIDlet {

    public MIDlet() {
        super();
        setLocationRelativeTo(null);
    }

    public void startApp() {
        Manager manager = (Manager)getManager();
        try {
            if (manager == null) {
                Debug.setMIDlet(this);
                manager = Manager.getInstance(this);
            }
            manager.startApp();
        } catch (Exception e) {
            Debug.logEx(e);
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
            Debug.logEx(e);
        }

    }

    @Override
    protected com.geargames.Manager getManager() {
        return Manager.getInstance();
    }
}
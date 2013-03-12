package com.geargames.awtdemo.application;

import com.geargames.ConsoleDebug;
import com.geargames.common.env.SystemEnvironment;

public final class MIDlet extends com.geargames.MIDlet {

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
            ((ConsoleDebug) SystemEnvironment.getInstance().getDebug()).logEx(e);
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
            ((ConsoleDebug)SystemEnvironment.getInstance().getDebug()).logEx(e);
        }

    }

    @Override
    protected com.geargames.Manager getManager() {
        return Manager.getInstance();
    }
}
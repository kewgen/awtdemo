package com.geargames.awtdemo.awt.components;

import com.geargames.awt.DrawablePElement;

/**
 * User: mikhail v. kutuzov, abarakov
 * Date: 25.12.12 23:38
 */
public abstract class DrawablePPanel extends DrawablePElement {
    public abstract void onHide();
    public abstract void onShow();

    public void show()
    {
        PPanelManager.getInstance().show(this);
    }

    public void showModal()
    {
        PPanelManager.getInstance().showModal(this);
    }

    public void hide()
    {
        PPanelManager.getInstance().hide(this);
    }

//    public void hideModal()
//    {
//        PPanelManager.getInstance().hideModal(/*this*/);
//    }
}

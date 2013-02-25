package com.geargames.awtdemo.timers;

/**
 * Класс таймера выполняющегося однократно.
 * User: abarakov
 * Date: 23.02.13 11:23
 */
public abstract class SingleTimer extends Timer {

    public boolean isMultiple() {
        return false;
    }

    public int getNextTimeActivate() {
        return -1;
    }

}
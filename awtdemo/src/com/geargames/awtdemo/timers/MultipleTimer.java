package com.geargames.awtdemo.timers;

/**
 * Класс таймера выполняющегося многократно.
 * User: abarakov
 * Date: 23.02.13 11:23
 */
public abstract class MultipleTimer extends Timer {

    public boolean isMultiple() {
        return true; //todo: не всегда true
    }

    public int getNextTimeActivate() {
        return getTimeActivate() + getInterval();
    }

}
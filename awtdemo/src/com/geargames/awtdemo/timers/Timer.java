package com.geargames.awtdemo.timers;

import com.geargames.awt.components.PElement;

/**
 * User: abarakov
 * Date: 23.02.13 11:22
 */
public abstract class Timer {

    private boolean enabled;
    // Время срабатывания таймера. Время в данном контексте это время относительно времени старта системы
    private int timeActivate; // nextActivate, lastActivated
    private int interval;
    private PElement element;

    protected Timer() {

    }

    /**
     * Вызывается при инициализации таймера
     */
    public void initiate() {

    }

    protected void needInitiate() {
        TimerManager.getInstance().timerRequiredInitiate(this);
    }

    public void optimize(int timeBase) {
        timeActivate -= timeBase;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            needInitiate();
        } else {
            TimerManager.getInstance().stopTimer(this);
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
        needInitiate();
    }

    public int getTimeActivate() {
        return timeActivate;
    }

    public abstract boolean isMultiple();

    // needNextActivation, needReActivation
    public abstract int getNextTimeActivate();

    /**
     * Запустить таймер.
     */
    public void start() {
        setEnabled(true);
    }

    /**
     * Остановить таймер.
     */
    public void stop() {
        setEnabled(false);
    }

    /**
     * Вызывается каждый раз при срабатывании таймера.
     */
    protected abstract void onTimer();

//    public boolean update(int elapsedTime) {
//        remainingTime -= elapsedTime;
//        if (remainingTime <= 0) {
//            onTimer(); //todo: А что если, с момента предыдущего апдейта, таймер должен был сработать дважды?
//            remainingTime = interval - remainingTime;
//            return true;
//        }
//        return false;
//    }

}
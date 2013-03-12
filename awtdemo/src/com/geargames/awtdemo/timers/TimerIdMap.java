package com.geargames.awtdemo.timers;

/**
 * User: abarakov
 * Date: 25.02.13 10:16
 * Список кодов клиентских таймеров.
 */
public class TimerIdMap extends com.geargames.awt.timers.TimerIdMap {
    /*
     * Пожалуйста, выбирайте id следуя следующему правилу:
     *     отрицательные id - для всех системных таймеров (таймеров общего кода);
     *     id = 0..19999 - для клиентских таймеров с константным id.
     */

    public final static int AWTDEMO_PROGRESSBARS_TICK = 1000;

}
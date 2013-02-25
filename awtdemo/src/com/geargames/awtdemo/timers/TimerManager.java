package com.geargames.awtdemo.timers;

import com.geargames.awt.components.PElement;
import com.geargames.common.util.ArrayList;
import com.geargames.common.util.HashMap;
import com.geargames.env.ConsoleEnvironment;

import java.util.Random;

/**
 * User: abarakov
 * Date: 23.02.13 8:54
 */
public class TimerManager {

    // ----- Instance management ---------------------------------------------------------------------------------------

    private static TimerManager instance;

    public static TimerManager getInstance() {
        if (instance == null) {
            instance = new TimerManager();
        }
        return instance;
    }

    // ----- Class fields ----------------------------------------------------------------------------------------------

    // Сортированный массив таймеров сортированный в порядке уменьшения времени, когда таймер должен сработать.
    // Т.е. первый таймер в списке - таймер, который сработает позже всех и наоборот, в конце списка таймер сработающий
    // раньше всех.
    private ArrayList timers; // LinkedList

    private HashMap timerIds;

//    // Список с обновленным порядком таймеров. Этот список используется во время работы метода update(). Например,
//    // сработал многократный таймер, следовательно этот таймер должен поменять свою позицию в списке, чтобы снова
//    // сработать в следующий раз.
//    private ArrayList newOrderedTimers;

    private ArrayList initiateTimers; // Список таймеров, требующих инициализации и размещения в основном списке таймеров.
    private ArrayList disabledTimers; // Список остановленных таймеров.
    private ArrayList unusedTimers;   // Список таймеров, ранее созданных, но не используемых в данный момент.

    private int lastTime;             // Время последнего обновления таймеров

    private boolean isUpdateWorked = false;                   // True, если в настоящий момент выполняется метод update().
//    private boolean isInitiatedNewOrderedTimerList = false; // True, если массив newOrderedTimers уже был актуально заполнен.
//    private boolean isTimerListChanged = false;             // True, если список таймеров был модифицирован.

    public TimerManager() {
        //todo: Какие значения capacity выбрать?
        timers         = new ArrayList(32);
        initiateTimers = new ArrayList(8);
        unusedTimers   = new ArrayList(8);
        lastTime = millisTime();
    }

    // ----- Property management ---------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Вернуть время в миллисекундах
      */
    public int millisTime() {
        return (int) ConsoleEnvironment.getInstance().nanoTime() / 1000000;
    }

    public final static int DINAMIC_TIMER_ID_LOW  = 10001;
    public final static int DINAMIC_TIMER_ID_HIGH = Integer.MAX_VALUE;

    private static Random random = null; //todo: Думается, что рандом-генератор должен быть один на всю программу

    /**
     * Вернуть id таймера, который еще не занят.
     * @return
     */
    // generateTimerId
    public int getNewTimerId() {
        if (random == null) {
            random = new Random();
        }
        // Если выбрать тип short как timerId, то при большом количестве созданных таймеров (>15000), данный метод может
        // начать тормозить.
        int id;
        do {
            id = random.nextInt(DINAMIC_TIMER_ID_HIGH - DINAMIC_TIMER_ID_LOW + 1) + DINAMIC_TIMER_ID_LOW;
        } while (timerIds.containsKey(id));
        return id;
    }

    /**
     * Создать однократный таймер.
     * @param interval
     * @param timerId - выбирайте id из следующих соображений:
     *                0..10000 - для таймеров с константным id,
     *                10001.. - для таймеров с динамическим id, который генерирует сам менеджер
     *                отрицательные id - для всех системных таймеров
     * @return
     */
    //todo: классы Drawable и PElement должны иметь одного предка с методо Event
    public int createTimer(int interval, int timerId, PElement callBackElement) {
        return timerId;
    }

    /**
     * Создать однократный таймер, причем, id таймеру будет присвоен самим менеджером
     * @param interval
     * @return
     */
    public int createTimer(int interval, PElement callBackElement) {
        return createTimer(interval, getNewTimerId(), callBackElement);
    }

    /**
     * Удалить таймер
     * @param timerId
     */
    public void deleteTimer(int timerId) {

    }

//    private ArrayList getModifyTimerList(/*boolean changeNeed*/) {
//        if (isUpdateWorked/* && changeNeed*/) {
//            if (!isInitiatedNewOrderedTimerList) {
//                if (newOrderedTimers == null) {
//                    newOrderedTimers = new ArrayList(timers.size());
//                } else {
//                    newOrderedTimers.ensureCapacity(timers.size());
//                }
//                // newOrderedTimers = timers.clone();
//                for (int i = 0; i < timers.size(); i++) {
//                    newOrderedTimers.set(i, timers.get(i));
//                }
//                isInitiatedNewOrderedTimerList = true;
//            }
//            return newOrderedTimers;
//        } else {
//            return timers;
//        }
//    }

    public void addTimer(Timer timer) {
//        int index = 0; //todo: вычислить позицию таймера в зависимости от его времени срабатывания
//        ArrayList list = getModifyTimerList(/*true*/);
//        list.add(index, timer);
//
//        if (list == newOrderedTimers) {
//            isTimerListChanged = true;
//        }
    }

    // removeTimer
    public void releaseTimer(Timer timer) {
//        // int indexTimer = timers.indexOf(timer);
//        ArrayList list = getModifyTimerList(/* indexTimer < currentIndexTimer*/);
//        list.remove(timer);
//
//        if (list == newOrderedTimers) {
//            isTimerListChanged = true;
//        }
    }

    public void stopTimer(Timer timer) {
        //todo: Безопасно удалить таймер изо всех списков и добавить в список disabledTimers.
        // Учесть, что останавливаемый таймер, может, в настоящий момент, перемещаться по списку в методе update()
    }

    /**
     * Добавить таймер timer в список на инициализацию.
     * @param timer
     */
    public void timerRequiredInitiate(Timer timer) {
        if (timer.getEnabled() && !initiateTimers.contains(timer)) {
            initiateTimers.add(timer);
        }
        //todo: а что, если таймер уже есть в списке timers?
    }

    private void insertTimer(Timer timer, int timeActivation) {
        // Данный код одновременно реализует две функциональности:
        // 1. Поиск места в списке, куда вставить элемент, с сохранением сортированности списка;
        // 2. Перемещение последнего элемента списка (многократного таймера) в новую позицию
        int i = timers.size() - 2;
        /* time examples:
           48 36 35 17 17 9 2 x
                   ^- 23
         */
        while (i >= 0) {
            Timer tmpTimer = (Timer)timers.get(i);
            if (timeActivation < tmpTimer.getTimeActivate()) {
                break;
            }
            timers.set(i + 1, tmpTimer);
            i--;
        }
        timers.set(i + 1, timer);
    }

    /**
     * Метод вызывается в цикле mainLoop.
     */
    public void update(/*int elapsedTime*/) {
        isUpdateWorked = true;
        try {
            lastTime = millisTime();

            // Инициализация таймеров
            for (int i = 0; i < initiateTimers.size(); i++) {
                Timer timer = (Timer)initiateTimers.get(i);
                timers.add(null);
                insertTimer(timer, timer.getTimeActivate());
            }

            //

            //

            boolean status = timers.size() > 0;
            while (status) {
                Timer timer = (Timer)timers.get(timers.size() - 1);
                if (timer.getTimeActivate() <= lastTime) {
                    timer.onTimer();

                    int nextTimeActivation = timer.getNextTimeActivate();
                    if (nextTimeActivation != -1) {
                        // Таймер требует повторной активации, потому должен быть обновлен и перемещен в списке.
                        insertTimer(timer, nextTimeActivation);
                    } else {
                        releaseTimer(timer);
                    }

                } else {
                    // Если очередной таймер не сработал, значит и другие, более поздние, не должны сработать.
                    status = false;
                }
            }
            /*
            int indexTimer = timers.size() - 1;
            while (indexTimer >= 0) {
                Timer timer = (Timer)timers.get(indexTimer);
                if ( !timer.update(elapsedTime) ) {
                    // Если очередной таймер не сработал, значит и другие, более поздние, не должны сработать.
                    break;
                }
                indexTimer--;
            }
            if (isTimerListChanged) {
                // swap list of timers
                ArrayList tmp = timers;
                timers = newOrderedTimers;
                newOrderedTimers = tmp;
                newOrderedTimers.clear();
                isTimerListChanged = false;
            }
            isInitiatedNewOrderedTimerList = false;
            */

            // Оптимизация таймеров

        } finally {
            isUpdateWorked = false;
        }
    }
}
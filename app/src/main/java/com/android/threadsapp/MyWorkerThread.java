package com.android.threadsapp;

/**
 * MyWorkerThread extends Thread - расширяем обычный Thread
 */
public class MyWorkerThread extends Thread {

    // запускаем бесконечный процесс и кладем туда какуюту работу.
    @Override
    public void run() {
        doWork();
    }

    //какаята работа
    private void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

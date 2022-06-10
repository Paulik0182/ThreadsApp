package com.android.threadsapp;

/**
 * MyWorkerThread extends Thread - расширяем обычный Thread
 */
public class MyWorkerThread extends Thread {

    /**
     * После выполнения работы в методе run(), данный поток умирает и его повторно вызвать нельзя.
     * Можно в данном методе запустить бесконечный цыкл, и завершать его по изменению условия.
     */

    // запускаем бесконечный процесс и кладем туда какуюту работу.
    @Override
    public void run() {
        while (!Thread.interrupted()) {// бесконечно будет выполнятся работа
            doWork();
        }
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

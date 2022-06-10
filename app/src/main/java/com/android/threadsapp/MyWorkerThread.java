package com.android.threadsapp;

import java.util.LinkedList;

/**
 * MyWorkerThread extends Thread - расширяем обычный Thread
 */
public class MyWorkerThread extends Thread {

    private final LinkedList<Runnable> queue = new LinkedList<Runnable>();//некая очередь

    /**
     * После выполнения работы в методе run(), данный поток умирает и его повторно вызвать нельзя.
     * Можно в данном методе запустить бесконечный цыкл, и завершать его по изменению условия.
     */

    // запускаем бесконечный процесс и кладем туда какуюту работу.
    @Override
    public void run() {
        while (!Thread.interrupted()) {// бесконечно будет выполнятся работа
            if (!queue.isEmpty()) {//если очередь не пуста
                queue.get(0).run();//берем первый элемент и запускаем его
                queue.remove(0);// далее удаляем элемент

            }
        }
    }

    private void post(Runnable runnable) {
        queue.add(runnable);//кладем в очередь дополнительную задачу
    }
}

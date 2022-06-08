package com.android.threadsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.threadsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notMainThread();

                Toast.makeText(MainActivity.this, "Старт", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //бесконечный цыкл
    private void doWork() {
        Thread currentThread = Thread.currentThread();//текущий поток в котором выполняется работа
        Log.d("@@@ doWork", currentThread.getName());//лог текущего потока
        currentThread.isInterrupted();//выесняем, нужно ли его (поток) приостановить.
        // isInterrupted() это проставления фложка (boolean переменная) стоит ли его закончить.
        // Потоки не убивают просто так, он должен закончить свою работу и завершить работу корректно.


        double x = Math.PI;
        // && currentThread.isInterrupted() - и пока не завершон
        while (x < 1_000_000_000_000_000d && currentThread.isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += x;
        }
        Toast.makeText(MainActivity.this, "Финиш", Toast.LENGTH_SHORT).show();
    }

    /**
     * В данных изменениях. Создается thread куда кладется работа myRunnable и запускается start()
     * В фоне запускается именно myRunnable() поток
     */
    //запускаем не на главном потоке (асинхронный, поралельный поток)
    //сам посебе Thread бесполезен, в него положить ничего нельзя.
    //Необходимо сделать наследник Thread
    private void notMainThread() {
        Thread thread = new Thread(new myRunnable(), "Это я, не главный поток");
        thread.start();
//        thread.interrupt();//рекомендация остановить
    }

    /**
     * У thread можно вызвать метод .start() или .run()
     * В случае вызвова метод .run() это будет всеволиш вызов метода, никакой асинхронной работы не будет.
     * А с вызовом .start() сначало будет запущена поралельная работа, а потом выполнение нужного кода.
     */

    //Необходимо сделать наследник Thread
    class myThread extends Thread {

        //метод который будет выполнятся код
        @Override
        public void run() {
            doWork();
        }
    }

    //наследуем метод Runnable (запускаемый объект)
    class myRunnable implements Runnable {
        @Override
        public void run() {
            doWork();
        }
    }
}
package com.android.threadsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.threadsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());//специальный объект.
    // это единственный способ положить задачу в очередь.
    //Можно положить Looper в главном потоке
    /**
     * Фишки потоков. Если глобально объявить переменную, то она будет доступна на любом потоке.
     */
    private double y = 0;//переменная доступная на любом потоке.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        y = 1d;

        binding.startButton.setOnClickListener(view -> {
            notMainThread();

            binding.resultTextView.setText("Старт!");

            Toast.makeText(MainActivity.this, "Старт", Toast.LENGTH_SHORT).show();
        });
    }

    private void doWork() {
        Log.d("@@@ doWork", Thread.currentThread().getName());//лог текущего потока
        Thread.currentThread().isInterrupted();//выесняем, нужно ли его (поток) приостановить.
        // isInterrupted() это проставления фложка (boolean переменная) стоит ли его закончить.
        // Потоки не убивают просто так, он должен закончить свою работу и завершить работу корректно.

        double x = Math.PI;
        // && currentThread.isInterrupted() - и пока не завершон
        while (x < 1_000_000_000_000d && !Thread.currentThread().isInterrupted()) {
            try {
                y = x;
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += x;
        }
        Log.d("@@@ doWork", Thread.currentThread().getName());//лог текущего потока

        //способ положить в очередь на главном потоке, в место run
        handler.post(() -> {
            Log.d("@@@ doWork", Thread.currentThread().getName());//лог текущего потока
            binding.resultTextView.setText("ФИНИШ!");
            //Поскольку мы находимся на главном потоке, мы можем кинуть Toast
            //на главном потоке Looper (Петля) есть.
            Toast.makeText(MainActivity.this, "ФИНИШ!", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * В данных изменениях. Создается thread куда кладется работа MyRunnable и запускается start()
     * В фоне запускается именно MyRunnable() поток
     */
    //запускаем не на главном потоке (асинхронный, поралельный поток)
    //сам посебе Thread бесполезен, в него положить ничего нельзя.
    //Необходимо сделать наследник Thread
    private void notMainThread() {
        Thread thread = new Thread(new MyRunnable(), "Это я, не главный поток");
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
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            doWork();
        }
    }

    //Класс для работы с Thread. был раньше, сейчас не используется
    //Недостаток способа - исчезает при повороте экрана
    class MyTask extends AsyncTask<String, Integer, String> {

        //Выполняем на главном потоке
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Метод который работает в фоне
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        //Выполняется после завершения метода doInBackground на главном потоке
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
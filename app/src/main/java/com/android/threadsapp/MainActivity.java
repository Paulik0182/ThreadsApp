package com.android.threadsapp;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.threadsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startMyWorkerThread();//запустили паралельный поток

//        returnToMainThread();
        Handler handler = new Handler();//данный код не корректно читать последовательно.
        handler.post(() -> {//после этой строчки сразу попадаем на метод initView().
            //работа будет выполнена тогда когда до него дойдет очередь.
            //todo (выполнения какойто работы.)
        });

        initView();

    }

    /**
     * onCreate - главный поток
     * В методе onCreate -> С начало стартует метод (запускаем, создаем поток) startMyWorkerThread()
     * потом, сразу, не дожидаясь выполнения работ метода startMyWorkerThread() переходим к
     * выполнению метода initView(). Предыдущий метод работает паралельно.
     */

    private void startMyWorkerThread() {
        Thread thread = new MyWorkerThread();
        thread.start();
    }

    private void initView() {
        //todo (инициализация View. это если нет binding)
    }

    private void returnToMainThread() {
        Handler handler = new Handler();
        handler.post(() -> {
            //todo (выполнения какойто работы)
        });
    }

}
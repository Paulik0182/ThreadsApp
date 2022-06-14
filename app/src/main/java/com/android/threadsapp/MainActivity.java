package com.android.threadsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int LOOPS_IN_THREADS = 500;

    private final Counter asyncCounter = new Counter();
    private final Counter syncCounter = new Counter();
    private int threadCounter = 0;
    private Button startButton;
    private TextView asyncCounterTv;
    private TextView syncCounterTv;
    private TextView threadCounterTv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        startButton.setOnClickListener((v) -> {
            threadCounter++; // действие по нажатию на кнопку в главном потоке
            threadCounterTv.setText("Exp: " + (threadCounter * LOOPS_IN_THREADS * 10));

            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
            new Thread(workRunnable).start();
        });
    }

    @SuppressLint("SetTextI18n")
    private final Runnable workRunnable = () -> {
        for (int i = 0; i < LOOPS_IN_THREADS; i++) {//цакличность выполнения
            asyncCounter.counter++;

            runOnUiThread(() -> {// Синхронная секция. Он становится в очередь на главный поток
                syncCounter.counter++;
            });
        }

        runOnUiThread(() -> {// обновление view с основным значением
            asyncCounterTv.setText("Async: " + asyncCounter.counter);
            syncCounterTv.setText("Sync: " + syncCounter.counter);
        });
    };

    private void initView() {

        startButton = findViewById(R.id.start_thread_button);
        asyncCounterTv = findViewById(R.id.async_counter_text_view);
        syncCounterTv = findViewById(R.id.sync_counter_text_view);
        threadCounterTv = findViewById(R.id.threads_counter_text_view);

    }
}
package com.android.threadsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long LOOPS_IN_THREADS = 500_000L;

    private final Counter asyncCounter = new Counter();
    private final Counter syncCounter = new Counter();
    private final int threadCounter = 0;
    private Button startButton;
    private TextView asyncCounterTv;
    private TextView syncCounterTv;
    private TextView threadCounterTv;
    @SuppressLint("SetTextI18n")
    private final Runnable workRunnable = () -> {
        for (int i = 0; i < LOOPS_IN_THREADS; i++) {//цакличность выполнения
            asyncCounter.inc();
        }
        runOnUiThread(() -> {// обновление view с основным значением
            asyncCounterTv.setText("Async: " + asyncCounter.getCounter());
        });
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        startButton.setOnClickListener((v) -> {
            asyncCounter.reset();
            syncCounter.reset();
            threadCounterTv.setText("Exp: " + (LOOPS_IN_THREADS * 10));

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

    private void initView() {

        startButton = findViewById(R.id.start_thread_button);
        asyncCounterTv = findViewById(R.id.async_counter_text_view);
        syncCounterTv = findViewById(R.id.sync_counter_text_view);
        threadCounterTv = findViewById(R.id.threads_counter_text_view);

    }
}
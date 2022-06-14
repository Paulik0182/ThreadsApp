package com.android.threadsapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int asyncCounter = 0;
    private final int syncCounter = 0;
    private final int threadCounter = 0;
    private Button startButton;
    private TextView asyncCounterTv;
    private TextView syncCounterTv;
    private TextView threadCounterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        startButton = findViewById(R.id.start_thread_button);
        asyncCounterTv = findViewById(R.id.async_counter_text_view);
        syncCounterTv = findViewById(R.id.sync_counter_text_view);
        threadCounterTv = findViewById(R.id.threads_counter_text_view);

    }
}
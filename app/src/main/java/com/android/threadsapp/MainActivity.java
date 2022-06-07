package com.android.threadsapp;

import android.os.Bundle;
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

                doWork();
                Toast.makeText(MainActivity.this, "Финиш", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //бесконечный цыкл
    private void doWork() {
        double x = Math.PI;
        while (x < 1_000_000_000_000_000d) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += x;
        }
    }

    //запускаем не на главном потоке (асинхронный, поралельный поток)
    //сам посебе Thread бесполезен, в него положить ничего нельзя.
    //Необходимо сделать наследник Thread
    private void notMainThread() {
        Thread thread = new Thread();
        thread.start();
        thread.interrupt();//рекомендация остановить
    }

}
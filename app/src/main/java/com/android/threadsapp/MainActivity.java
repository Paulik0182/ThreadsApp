package com.android.threadsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.threadsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    /**
     * Чтобы гарантировать, что обновления переменных предсказуемо распространяются на другие потоки,
     * мы должны применить летучий модификатор для этих переменных: volatile
     */
    private static final int r = 1;// видим переменную по всему классу
    private static int x = 1;// видим переменную по всему классу

    /**
     * В потоках нельзя быть уверенным в нормальной видимости переменной.
     * Например:
     * Thread thread = new Thread(new Runnable() {
     * public void run() {
     * x = y + 3;
     * .......
     * <p>
     * Класс Runnable() под капотом имеет ссылку на всю MainActivity и из нее достает переменные,
     * поэтому он прекрассно видет глабальные переменные (переменную х) - и это одна из причин
     * утечек памяти (часто забывают о том, что Runnable имеет ссылку на всю MainActivity), например
     * активити уже уничтожена, а Runnable удержал на нее ссылку и она живет гдета в фоне и занимает память.
     * <p>
     * А с доступом к переменной y есть проблема. Переменная y объявлена в методе onCreate и данная
     * переменная живет пока не закроется скобка } у метода, не выполнится метод. Поэтому Runnable
     * имея ссылку на активити видит только глобальные переменные класса а методы исполняют код и закрываются,
     * тоесть - переменные существуют до тех пор пока метод вызывается, исполняет работу, потом переменная
     * прекращает свое существование. Но поскольку сохранить ссылку на переменную "y" не возможно,
     * можно сохранить значение данной переменной, поэтому эту переменную нужно сделать final, в этом
     * случае Runnable закэширует значение переменной и все.
     * <p>
     * Все потоки имеют доступ к одной памяти. Хоть пять Thread запустить, все они будут иметь доступ MainActivity,
     * а память у них однаи.
     * Но Thread может кэшировать себе значение (запомнить себе значение на время работы потока),
     * в связи с этим могут возникать проблемы когда Thread закэшировал "х" , а он процессе работы
     * уже поменялся, а уже после Thread возьмет и обновит значения "х".
     * Есть другая проблема, когда работают несколько потоков и они кэшируют значеня, а после пытаются изменить состояния
     * это может привести к конфликту.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final int y = 2; // видим переменную только в данном методе
        int z = 3;

        startMyWorkerThread();//запустили паралельный поток

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                x = y + 3;
            }
        });
        thread.start();

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
        MyWorkerThread MyWorkerThread = new MyWorkerThread();//1.  запустили поток
        MyWorkerThread.start();

        MyWorkerThread.post(() -> {//3.  добавили задачу
            try {
                Thread.sleep(3_000);//4.  спим какоето время
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            returnToMainThread();//5. выполняем какуюту работу
        });

    }

    private void initView() {
        //todo (инициализация View. это если нет binding)
    }

    private void returnToMainThread() {
        Handler handler = new Handler(Looper.getMainLooper());//5.1  создали Handler внутри главного потока
        handler.post(() -> {//5.2  сразу отдаем выполнение на главный поток
            Toast.makeText(this, "Привет", Toast.LENGTH_SHORT).show();
        });
    }

}
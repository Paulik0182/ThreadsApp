package com.android.threadsapp;

/**
 * создаем объект синглтон Singleton
 */
public class MySingleton {

    //НО благодаря методу createNewInstance() появляется следующая возможность.
    //В указанном методе ставим условие для создания только одного экземпляра.
    private volatile static MySingleton INSTANCE = null;

    //необходимо запретить создавать более одного Singleton, для этого мы сделали конструктор
    // приватный, но нам необходимо создать один объект
    private MySingleton() {

    }

    //делаем возвращаемый метод чтобы можно было создать объект
    //посути это тоже самое что вызвать конструктор
    //Данное написания кода наиболее корректно. Самый безопасный вариант.
    public static MySingleton getInstance() {

        final MySingleton current = INSTANCE;

        if (current == null) {
            synchronized (MySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MySingleton();
                } else {//можно и не писать
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }
}

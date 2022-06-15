package com.android.threadsapp;

/**
 * создаем объект синглтон Singleton
 */
public class MySingleton {

    //НО благодаря методу createNewInstance() появляется следующая возможность.
    //В указанном методе ставим условие для создания только одного экземпляра.
    private static final MySingleton INSTANCE = null;

    //необходимо запретить создавать более одного Singleton, для этого мы сделали конструктор
    // приватный, но нам необходимо создать один объект
    private MySingleton() {

    }

    //делаем возвращаемый метод чтобы можно было создать объект
    //посути это тоже самое что вызвать конструктор
    public static MySingleton createNewInstance() {
        return new MySingleton();
    }
}

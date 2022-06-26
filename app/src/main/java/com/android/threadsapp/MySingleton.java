package com.android.threadsapp;

/**
 * создаем объект синглтон Singleton
 * Этого кода достаточно для Андройда.
 * Можно не заморачиватся, так как на телефоне не будет множество потоков
 * Данный метод считается антипатерн и лучше делать четез app класс.
 */
public class MySingleton {

    public final static MySingleton INSTANCE = new MySingleton();

}

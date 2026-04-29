package org.mmendoza.exercise4_deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    // Dos objetos que actuarán como bloqueos
    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    public static void main(String[] args) {
        // Hilo 1: Bloquea Lock1 y espera Lock2
        Thread hilo1 = new Thread(() -> {
            synchronized (Lock1) {
                System.out.println("Hilo 1: Obtenido Lock1, esperando Lock2...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                synchronized (Lock2) {
                    System.out.println("Hilo 1: Obtenido Lock2");
                }
            }
        });

        // Hilo 2: Bloquea Lock2 y espera Lock1
        Thread hilo2 = new Thread(() -> {
            synchronized (Lock1) {
                System.out.println("Hilo 2: Obtenido Lock2, esperando Lock1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                synchronized (Lock2) {
                    System.out.println("Hilo 2: Obtenido Lock1");
                }
            }
        });

        hilo1.start();
        hilo2.start();
    }
}

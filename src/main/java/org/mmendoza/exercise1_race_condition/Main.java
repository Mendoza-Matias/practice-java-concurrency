package org.mmendoza.exercise1_race_condition;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        CounterAtomicInteger counter = new CounterAtomicInteger();

        long start = System.currentTimeMillis();

        // Array para almacenar los hilos
        Thread[] hilos = new Thread[1000];

        for (int i = 0; i < 1000; i++) {
            int numeroHilo = i;

            Runnable tarea = () -> {
                System.out.println("Hilo " + numeroHilo + " ejecutándose");
                counter.increment();
            };

            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        // CRÍTICO: Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            hilo.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("total time: " + (end - start));
        System.out.println("Contador final: " + counter.getCounter());
    }
}

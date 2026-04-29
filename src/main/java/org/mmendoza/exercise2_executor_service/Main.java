package org.mmendoza.exercise2_executor_service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Integer> userIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        long start = System.currentTimeMillis();

        for (Integer userId : userIds) {
            executor.execute(() -> { //Cada hilo lee un valor
                try {
                    Thread.sleep(100);
                    System.out.println("User id: " + userId + " - Thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start));
    }
}

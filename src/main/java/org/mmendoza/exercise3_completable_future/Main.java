package org.mmendoza.exercise3_completable_future;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Main {

    // objetos de prueba
    public record User(
            Integer id,
            String name
    ) {
        @Override
        public Integer id() {
            return id;
        }
    }

    public record Order(
            Integer id,
            User user, // user
            BigDecimal price
    ) {
        @Override
        public User user() {
            return user;
        }
    }

    private static final List<User> users = Arrays.asList(
            new User(1, "John Doe"),
            new User(2, "Lucas Perez"),
            new User(3, "Jane Doe")
    );

    private static final List<Order> orders = List.of(
            new Order(1, new User(1, "John Doe"), BigDecimal.TEN),
            new Order(2, new User(1, "John Doe"), BigDecimal.TEN)
    );

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Buscando usuario...");
                    return findUserById(1).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                })
                .thenApply(user -> {
                    System.out.println("Buscando órdenes para: " + user.name());
                    return findOrders(user);
                })
                .thenCompose(ordersList -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("Calculando total...");
                    return calculateTotal(ordersList);
                }))
                .thenApply(total -> {
                    System.out.println("Enviando notificación...");
                    sendNotification(total);
                    return total;
                })
                .exceptionally(ex -> {
                    System.err.println("Error en el pipeline: " + ex.getMessage());
                    sendNotification(BigDecimal.ZERO); // Notificar con total 0
                    return BigDecimal.ZERO;
                })
                .thenAccept(total -> {
                    long end = System.currentTimeMillis();
                    System.out.println("Pipeline completado: $" + total);
                    System.out.println("Tiempo total: " + (end - start) + "ms");
                });

        future.join(); // Esperar a que termine
    }

    public static Optional<User> findUserById(Integer id) {

        simulateLatency(500);

        return users.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    public static List<Order> findOrders(User user) {

        simulateLatency(800);

        return orders.stream().filter(order -> order.user().equals(user)).toList();
    }

    public static BigDecimal calculateTotal(List<Order> orders) {

        BigDecimal total = BigDecimal.ZERO;

        for (Order order : orders) {
            total = total.add(order.price());
        }
        return total;
    }

    public static void sendNotification(BigDecimal total) {
        simulateLatency(300);
        System.out.println("Notification sent! Total: $" + total);
    }

    private static void simulateLatency(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

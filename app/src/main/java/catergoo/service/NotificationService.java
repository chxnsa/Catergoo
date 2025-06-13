package catergoo.service;

import catergoo.model.Order;
import catergoo.model.User;
import catergoo.manager.SessionManager;
import javafx.application.Platform;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

public class NotificationService implements Runnable {

    private static NotificationService instance;
    private Thread serviceThread;
    private volatile boolean isRunning = false;
    private final int UPDATE_INTERVAL = 30000;
    private final Random random = new Random();

    private NotificationService() {
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }
        return instance;
    }

    public synchronized void startService() {
        if (!isRunning) {
            isRunning = true;
            serviceThread = new Thread(this, "NotificationService-Thread");
            serviceThread.setDaemon(true);
            serviceThread.start();
            System.out.println("NotificationService started - updating order status every 30 seconds");
        }
    }

    public synchronized void stopService() {
        if (isRunning) {
            isRunning = false;
            if (serviceThread != null) {
                serviceThread.interrupt();
            }
            System.out.println("NotificationService stopped");
        }
    }

    @Override
    public void run() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            try {
                updateOrderStatuses();
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println("NotificationService interrupted");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error in NotificationService: " + e.getMessage());
            }
        }
        isRunning = false;
    }

    private void updateOrderStatuses() {
        List<User> registeredUsers = SessionManager.getRegisteredUsers();

        if (registeredUsers == null || registeredUsers.isEmpty()) {
            return;
        }

        for (User user : registeredUsers) {
            if (user.getOrderHistory() != null && !user.getOrderHistory().isEmpty()) {
                updateUserOrderStatuses(user);
            }
        }
    }

    private void updateUserOrderStatuses(User user) {
        List<Order> orderHistory = user.getOrderHistory();

        for (Order order : orderHistory) {
            if (shouldUpdateOrderStatus(order)) {
                String newStatus = getNextStatus(order.getStatus());

                if (!newStatus.equals(order.getStatus())) {
                    Platform.runLater(() -> {
                        order.setStatus(newStatus);
                        System.out.println("Order " + order.getOrderId() + " status updated to: " + newStatus);
                    });
                }
            }
        }
    }

    private boolean shouldUpdateOrderStatus(Order order) {
        if ("Selesai".equals(order.getStatus()) || "Dibatalkan".equals(order.getStatus())) {
            return false;
        }

        LocalDateTime orderTime = order.getOrderDate();
        LocalDateTime now = LocalDateTime.now();
        long secondsSinceOrder = ChronoUnit.SECONDS.between(orderTime, now);

        switch (order.getStatus()) {
            case "Menunggu Konfirmasi":
                return secondsSinceOrder >= 60 && random.nextInt(100) < 40;
            case "Diproses":
                return secondsSinceOrder >= 120 && random.nextInt(100) < 35;
            case "Sedang Dikirim":
                return secondsSinceOrder >= 300 && random.nextInt(100) < 25;
            default:
                return false;
        }
    }

    private String getNextStatus(String currentStatus) {
        switch (currentStatus) {
            case "Menunggu Konfirmasi":
                return "Diproses";
            case "Diproses":
                return "Sedang Dikirim";
            case "Sedang Dikirim":
                return "Selesai";
            default:
                return currentStatus;
        }
    }

    public boolean isServiceRunning() {
        return isRunning;
    }

    public void forceUpdateAllOrders() {
        if (isRunning) {
            new Thread(() -> {
                try {
                    updateOrderStatuses();
                } catch (Exception e) {
                    System.err.println("Error during force update: " + e.getMessage());
                }
            }).start();
        }
    }

    public void shutdown() {
        stopService();
        try {
            if (serviceThread != null && serviceThread.isAlive()) {
                serviceThread.join(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
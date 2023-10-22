package com.obsidian.knightsmp.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadManager {
    private static final Map<String, CustomFutureTask<?>> threadMap = new HashMap<>();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    // Create a new thread
    public static void createThread(String threadName, Runnable task) {
        CustomFutureTask<?> futureTask = new CustomFutureTask<>(threadName, task);
        executor.submit(futureTask);
        threadMap.put(threadName, futureTask);
    }

    // List all threads
    public static void listThreads() {
        System.out.println("Active Threads:");
        for (String threadName : threadMap.keySet()) {
            System.out.println(threadName);
        }
    }

    // Edit a thread (replace the old one with a new task)
    public static void editThread(String threadName, Runnable newTask) {
        CustomFutureTask<?> oldFuture = threadMap.get(threadName);
        if (oldFuture != null) {
            oldFuture.cancel(true); // Cancel the old task
        }

        CustomFutureTask<?> newFuture = new CustomFutureTask<>(threadName, newTask);
        executor.submit(newFuture);
        threadMap.put(threadName, newFuture);
    }

    // Send data to a thread
    public static void sendDataToThread(String threadName, Object data) {
        CustomFutureTask<?> futureTask = threadMap.get(threadName);
        if (futureTask != null) {
            futureTask.setData(data);
        }
    }

    // Receive data from a thread
    public static Object receiveDataFromThread(String threadName) {
        CustomFutureTask<?> futureTask = threadMap.get(threadName);
        if (futureTask != null) {
            return futureTask.getData();
        }
        return null;
    }

    // Delete a thread
    public static void deleteThread(String threadName) {
        CustomFutureTask<?> futureTask = threadMap.get(threadName);
        if (futureTask != null) {
            futureTask.cancel(true);
            threadMap.remove(threadName);
        }
    }

    // Shutdown the executor service when done
    public static void shutdown() {
        executor.shutdown();
    }

    // Custom implementation of FutureTask with data storage
    private static class CustomFutureTask<V> extends FutureTask<V> {
        private final String threadName;
        private Object data;

        public CustomFutureTask(String threadName, Runnable runnable) {
            super(() -> {
                runnable.run();
                return null;
            });
            this.threadName = threadName;
        }

        @Override
        protected void done() {
            super.done();
            // Remove the thread from the map when it's done
            threadMap.remove(threadName);
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Your example usage goes here
    }
}

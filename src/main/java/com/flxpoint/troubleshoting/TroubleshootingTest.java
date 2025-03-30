package com.flxpoint.troubleshoting;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TroubleshootingTest {
    private static final Logger logger = Logger.getLogger(TroubleshootingTest.class.getName());
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    //converted the hasmap to concurrentHashmap to make it thread safe
    private static final Map<String, Integer> sharedData = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        TroubleshootingTest test = new TroubleshootingTest();
        test.runTest();
    }

    public void runTest() throws InterruptedException {
        List<Future<?>> results = new ArrayList<>();

        results.add(executor.submit(() -> {
            int value =  updateSharedData("key1");
            logger.info("Updated key1 to: " + value);}));

        results.add(executor.submit(() -> {
            int value = updateSharedData("key1");
            logger.info("Updated key1 to: " + value);
        }));

        //catching NullPointerException
        results.add(executor.submit(() -> {
            try {
                int result = causeNullPointer();
                logger.info("Result from causeNullPointer: " + result);
            } catch (NullPointerException e) {
                logger.log(Level.WARNING, "Caught NullPointerException in causeNullPointer", e);
            }
        }));

        //catching NumberFormatException
        results.add(executor.submit(() -> {
            try {
                int result = parseInteger("ABC");
                logger.info("Parsed integer: " + result);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Caught NumberFormatException in parseInteger", e);
            }
        }));

        Object lock1 = new Object();
        Object lock2 = new Object();
        //prevent deadlock by reordering the locks, initialy the first thread acquired lock1 and thread 2 acquired lock2, and they both wait for each other. hence changing the order of the locks would prevent the dead lock condition
        executor.submit(() -> deadlockMethod(lock1, lock2, "method1"));
        executor.submit(() -> deadlockMethod(lock1, lock2, "method2"));

        //executor.submit(() -> missingMethod()); //no method called missingMethod(), removing it.

        //String num = 100; //removing this variable as it is not used.

        //catch exception
        executor.submit(() -> {
            try {
                methodThrowsException();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Caught Exception in methodThrowsException", e);
            }
        });


        Future<?> infiniteLoopFuture = executor.submit(() -> infiniteLoop());
        results.add(infiniteLoopFuture);
        try {
            // Wait for up to 1 second for the task to complete.
            infiniteLoopFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            logger.info("Infinite loop task timed out and was cancelled.");
            infiniteLoopFuture.cancel(true);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception in infiniteLoop", e);
        }

        results.add(executor.submit(this::unclosedScanner));

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    // causes race condition without synchronized as hashmaps are not thread safe.
    private synchronized Integer updateSharedData(String key) {
        int currentValue = sharedData.getOrDefault(key, 0);
        sharedData.put(key, currentValue + 1);
        return currentValue + 1;
    }

    private Integer causeNullPointer() {
        String str = null;
        if (str == null) {
            throw new NullPointerException("str is null");
        }
        return str.length();
    }

    private Integer parseInteger(String value) {
        return Integer.parseInt(value);
    }

    private void deadlockMethod(Object lock1, Object lock2, String method) {
        synchronized (lock1) {
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            synchronized (lock2) {
                System.out.println("Acquired both locks for " +  method);
                logger.info("Acquired both locks for " +  method);
            }
        }
    }



    private void infiniteLoop() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Infinite loop interrupted");
                return;
            }
            // Optionally, you can add a small sleep here to reduce CPU usage:
            // try { Thread.sleep(10); } catch (InterruptedException e) { return; }
        }
    }

    //scanner is not closed
    private void unclosedScanner() {
        try(Scanner scanner = new Scanner(System.in))   {
            System.out.println("Enter something: ");
            String input = scanner.nextLine();
            System.out.println("You entered: " + input);
        }catch (Exception e) {
            logger.log(Level.WARNING, "Exception in unclosedScanner", e);
        }


    }

    private void methodThrowsException() throws Exception {
        throw new Exception("Test Exception");
    }
}
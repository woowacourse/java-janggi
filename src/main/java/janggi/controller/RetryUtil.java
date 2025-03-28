package janggi.controller;

import java.util.function.Supplier;

public class RetryUtil {
    private static final int MAX_RETRIES = 100;

    public static void processWithRetry(Runnable runnable) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
                attempts++;
            }
        }
        throw new IllegalStateException("최대 재시도 횟수(" + MAX_RETRIES + ")를 초과했습니다. 프로그램을 종료합니다.");
    }

    public static <T> T getWithRetry(Supplier<T> task) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }
        throw new IllegalStateException("최대 재시도 횟수(" + MAX_RETRIES + ")를 초과했습니다. 프로그램을 종료합니다.");
    }
}

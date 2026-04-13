package janggi.util;

import janggi.exception.PersistenceTimeoutException;
import java.util.function.Supplier;

public class PersistenceExecutor {

    private static final int MAX_RETRY_COUNT = 3;

    public static <T> T retryOnTimeout(Supplier<T> supplier, Runnable onTimeout) {
        return retryOnTimeout(supplier, onTimeout, 0);
    }

    public static void retryOnTimeout(Runnable action, Runnable onTimeout) {
        retryOnTimeout(() -> {
            action.run();
            return null;
        }, onTimeout, 0);
    }

    private static <T> T retryOnTimeout(Supplier<T> supplier, Runnable onTimeout, int retryCount) {
        try {
            return supplier.get();
        } catch (PersistenceTimeoutException e) {
            int nextRetryCount = retryCount + 1;
            checkRetryCount(e, nextRetryCount);
            onTimeout.run();
            return retryOnTimeout(supplier, onTimeout, nextRetryCount);
        }
    }

    private static void checkRetryCount(PersistenceTimeoutException e, int retryCount) {
        if (retryCount >= MAX_RETRY_COUNT) {
            throw e;
        }
    }
}

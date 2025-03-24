package util;

import java.util.function.Supplier;

public class ErrorHandler {

    public static void retryUntilSuccess(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> T retryUntilSuccess(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

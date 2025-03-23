package janggi.utils;

import java.util.function.Supplier;

public class ExceptionHandler {

    public static void retry(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            retry(action);
        }
    }

    public static <T> T repeat(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return repeat(supplier);
        }
    }
}

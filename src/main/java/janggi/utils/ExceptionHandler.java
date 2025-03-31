package janggi.utils;

import java.util.function.Supplier;

public class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static <T> T retry(final Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return retry(action);
        }
    }
}

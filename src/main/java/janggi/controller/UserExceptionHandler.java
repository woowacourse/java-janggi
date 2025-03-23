package janggi.controller;

import java.util.function.Supplier;

public class UserExceptionHandler {

    protected static <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    protected static void retryUntilSuccess(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

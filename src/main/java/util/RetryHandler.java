package util;

import java.util.function.Supplier;

public class RetryHandler {

    private RetryHandler() {
    }

    public static <T> T retryInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

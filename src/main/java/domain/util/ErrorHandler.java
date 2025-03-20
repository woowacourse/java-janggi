package domain.util;

import java.util.function.Supplier;

public class ErrorHandler {

    public static <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

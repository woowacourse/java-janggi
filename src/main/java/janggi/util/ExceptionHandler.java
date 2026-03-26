package janggi.util;

import janggi.view.OutputView;
import java.util.function.Supplier;

public class ExceptionHandler {

    public static <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }

    public static void retryUntilSuccess(Runnable runnable) {
        retryUntilSuccess(() -> {
            runnable.run();
            return null;
        });
    }
}

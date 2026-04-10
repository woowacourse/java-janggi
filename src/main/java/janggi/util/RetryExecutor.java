package janggi.util;

import janggi.view.OutputView;
import java.util.function.Supplier;

public class RetryExecutor {

    public static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public static void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalStateException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}

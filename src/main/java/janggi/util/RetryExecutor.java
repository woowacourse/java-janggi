package janggi.util;

import janggi.view.OutputView;
import java.util.function.Supplier;

public class RetryExecutor {

    public static <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return retry(supplier);
        }
    }

    public static void retry(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalStateException e) {
            OutputView.printErrorMessage(e.getMessage());
            retry(runnable);
        }
    }
}

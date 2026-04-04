package janggi.utils;

import janggi.view.OutputView;
import java.util.function.Supplier;

public final class RetryExecutor {
    private RetryExecutor() {
    }

    public static <T> T retry(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return retry(supplier);
        }
    }
}

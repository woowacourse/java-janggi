package janggi.utils;

import janggi.view.OutputView;
import java.util.function.BiFunction;
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

    public static <T, U, R> R retry(final BiFunction<T, U, R> biFunction, T t, U u) {
        try {
            return biFunction.apply(t, u);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return retry(biFunction, t, u);
        }
    }
}

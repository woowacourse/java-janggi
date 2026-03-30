package janggi.utils;

import janggi.view.OutputView;
import java.util.function.BiFunction;
import java.util.function.Function;
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

    public static <T, R> R retry(final Function<T, R> function, final T t) {
        try {
            return function.apply(t);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return retry(function, t);
        }
    }

    public static <T, U, R> R retry(final BiFunction<T, U, R> biFunction, final T t, final U u) {
        try {
            return biFunction.apply(t, u);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return retry(biFunction, t, u);
        }
    }
}

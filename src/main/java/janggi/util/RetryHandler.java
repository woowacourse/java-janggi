package janggi.util;

import janggi.view.OutputView;
import java.util.function.Supplier;

public final class RetryHandler {

    private RetryHandler() {
    }

    public static <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}

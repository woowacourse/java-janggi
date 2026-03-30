package janggi.util;

import janggi.view.OutputView;
import java.util.function.Supplier;

public class ActionExecutor {

    public static <T> T retryUntilSuccess(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            return retryUntilSuccess(supplier);
        }
    }
}

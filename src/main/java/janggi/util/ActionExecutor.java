package janggi.util;

import janggi.view.output.OutputView;
import java.util.function.Supplier;

public class ActionExecutor {

    public static <T> T retryUntilSuccess(Supplier<T> supplier, OutputView outputView) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printMessage(e.getMessage());
            return retryUntilSuccess(supplier, outputView);
        }
    }
}

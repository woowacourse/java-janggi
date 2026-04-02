package view;

import java.util.function.Supplier;

public class InputHandler {

    private InputHandler() {
    }

    public static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    public static void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}

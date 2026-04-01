package view;

import java.util.function.Supplier;

public class InputHandler {

    private InputHandler() {}

    public static <T> T readUntilValid(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}

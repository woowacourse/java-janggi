package controller;

import java.util.function.Supplier;
import view.OutputView;

public class RetryInput {

    public RetryInput() {
    }

    public static <T> T read(Supplier<T> reader) {
        while (true) {
            try {
                return reader.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}


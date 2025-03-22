package controller;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InputProcessor {

    public static <T> T repeatUntilNormalInput(Supplier<T> supplier, Consumer<String> consumer) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                consumer.accept(e.getMessage());
            }
        }
    }

    public static void repeatUntilNormalInput(Runnable runnable, Consumer<String> consumer) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (Exception e) {
                consumer.accept(e.getMessage());
            }
        }
    }
}

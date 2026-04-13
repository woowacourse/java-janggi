package console.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ExceptionHandler {
    private ExceptionHandler() {
    }

    public static <T> T handleRetry(Supplier<T> task, Consumer<String> consumer) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                consumer.accept(e.getMessage());
            }
        }
    }

    public static void handleRetry(Runnable task, Consumer<String> consumer) {
        while (true) {
            try {
                task.run();
                return;
            } catch (IllegalArgumentException e) {
                consumer.accept(e.getMessage());
            }
        }
    }

    public static void handle(Runnable task, Consumer<String> consumer) {
        try {
            task.run();
        } catch (IllegalArgumentException e) {
            consumer.accept(e.getMessage());
        }
    }
}

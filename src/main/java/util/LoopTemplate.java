package util;

import java.util.function.Supplier;

public class LoopTemplate {

    private LoopTemplate() {
    }

    public static <T> T tryCatchLoop (final Supplier<T> callback) {
        try {
            return callback.get();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            return tryCatchLoop(callback);
        }
    }

    public static void tryCatchLoop(final Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            tryCatchLoop(runnable);
        }
    }
}

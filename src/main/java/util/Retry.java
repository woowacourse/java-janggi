package util;

import java.util.function.Consumer;

public final class Retry {
    private Retry() {

    }

    public static <T> T untilSuccess(SupplierWithEx<T> supplier, Consumer<Exception> onError) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                onError.accept(e);
            }
        }
    }

    @FunctionalInterface
    public interface SupplierWithEx<T> {
        T get();

    }
}

package util;

import view.OutputView;

public final class Retry {
    private Retry() {

    }

    public static <T> T untilSuccess(SupplierWithEx<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public static void run(RunnableWithEx runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface SupplierWithEx<T> {
        T get();
    }

    @FunctionalInterface
    public interface RunnableWithEx {
        void run();
    }
}

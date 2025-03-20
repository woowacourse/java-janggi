package janggi.util;

import janggi.view.Viewer;
import java.util.function.Supplier;

public class RecoveryUtil {

    private static final Viewer VIEWER = new Viewer();

    private RecoveryUtil() {
    }

    public static void executeWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                VIEWER.printErrorMessage(e);
            }
        }
    }

    public static <T> T executeWithRetry(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                VIEWER.printErrorMessage(e);
            }
        }
    }
}
package view;

import java.util.function.Supplier;

public class RecoveryUtil {

    public static void executeWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (RuntimeException e) {
                System.out.println();
                System.out.println("[ERROR] " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (RuntimeException e) {
                System.out.println();
                System.out.println("[ERROR] " + e.getMessage());
                System.out.println();
            }
        }
    }
}

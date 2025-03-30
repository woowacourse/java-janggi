package janggi.util;

import janggi.view.Viewer;

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
}

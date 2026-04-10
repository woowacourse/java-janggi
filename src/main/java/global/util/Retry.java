package global.util;

import java.util.function.Supplier;

public class Retry {

    private Retry() {}

    public static void retry(Runnable action) {
        while (true) {
            try {
                action.run();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> T retry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

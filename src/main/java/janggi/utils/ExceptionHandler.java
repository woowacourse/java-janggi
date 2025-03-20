package janggi.utils;

public class ExceptionHandler {

    public static void retry(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            retry(action);
        }
    }
}

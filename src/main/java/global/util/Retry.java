package global.util;

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
}

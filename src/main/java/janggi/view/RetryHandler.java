package janggi.view;

public class RetryHandler {
    public static void retryUntilSuccess(InputAction inputAction) {
        try {
            inputAction.run();
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("[ERROR] " + exception.getMessage());
            retryUntilSuccess(inputAction);
        }
    }

    public static <T> T retryUntilSuccess(InputHandler<T> inputHandler) {
        try {
            return inputHandler.run();
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("[ERROR] " + exception.getMessage());
            return retryUntilSuccess(inputHandler);
        }
    }
}

package janggi.view;

import java.util.Optional;

public class RetryHandler {
    public static void retryUntilSuccess(InputAction inputAction) {
        for (boolean success = false; !success; ) {
            success = tryRun(inputAction);
        }
    }

    private static boolean tryRun(InputAction inputAction) {
        try {
            inputAction.run();
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return false;
        }
    }

    public static <T> T retryUntilSuccess(InputHandler<T> inputHandler) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryRun(inputHandler);
        }
        return result.get();
    }

    private static <T> Optional<T> tryRun(InputHandler<T> inputHandler) {
        try {
            return Optional.of(inputHandler.run());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return Optional.empty();
        }
    }
}

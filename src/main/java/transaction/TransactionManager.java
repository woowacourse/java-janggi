package transaction;

import java.util.function.Supplier;

public interface TransactionManager {

    <T> T execute(Supplier<T> action);

    default void execute(Runnable action) {
        execute(() -> {
            action.run();
            return null;
        });
    }
}

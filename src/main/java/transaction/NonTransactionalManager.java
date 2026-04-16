package transaction;

import java.util.function.Supplier;

public final class NonTransactionalManager implements TransactionManager {

    public static final NonTransactionalManager INSTANCE = new NonTransactionalManager();

    private NonTransactionalManager() {
    }

    @Override
    public <T> T execute(Supplier<T> action) {
        return action.get();
    }
}

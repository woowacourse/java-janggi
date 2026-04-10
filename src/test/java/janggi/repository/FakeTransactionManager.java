package janggi.repository;

import java.util.function.Supplier;

public class FakeTransactionManager extends TransactionManager {

    public FakeTransactionManager() {
        super(null);
    }

    @Override
    public void execute(Runnable callback) {
        callback.run();
    }

    @Override
    public <T> T execute(Supplier<T> callback) {
        return callback.get();
    }
}

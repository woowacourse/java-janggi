package persistence.transaction;

import java.sql.SQLException;

public interface SqlConsumer<T> {

    void accept(T t) throws SQLException;
}

package infrastructure;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalTask {
    void execute() throws SQLException;
}

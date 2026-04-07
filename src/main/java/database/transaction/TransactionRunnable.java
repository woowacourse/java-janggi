package database.transaction;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionRunnable {

    void run() throws SQLException;

}

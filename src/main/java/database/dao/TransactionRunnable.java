package database.dao;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionRunnable {

    void run() throws SQLException;

}

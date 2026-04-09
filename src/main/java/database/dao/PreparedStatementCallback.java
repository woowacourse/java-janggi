package database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementCallback<T> {

    T execute(PreparedStatement preparedStatement) throws SQLException;

}

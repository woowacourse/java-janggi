package database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface BatchPreparedStatementSetter<T> {
    void setValues(PreparedStatement ps, T item) throws SQLException;
}

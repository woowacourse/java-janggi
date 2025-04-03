package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {
    T mapFromResultSet(ResultSet resultSet) throws SQLException;
}

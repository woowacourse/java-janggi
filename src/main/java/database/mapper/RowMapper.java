package database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {

    <T> T map(ResultSet resultSet) throws SQLException;

}

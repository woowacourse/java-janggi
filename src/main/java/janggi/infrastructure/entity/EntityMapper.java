package janggi.infrastructure.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<R> {

    R map(ResultSet resultSet) throws SQLException;
}

package janggi.repository.entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterMapper {

    void map(PreparedStatement preparedStatement) throws SQLException;
}

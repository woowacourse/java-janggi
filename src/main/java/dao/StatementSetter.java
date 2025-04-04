package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementSetter {
    void setValues(PreparedStatement preparedStatement) throws SQLException;
}

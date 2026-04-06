package db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public enum StatementMode {
    DEFAULT(Connection::prepareStatement),
    RETURN_GENERATED_KEY((connection, sql) -> connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)),
    ;

    private final PreparingStatement preparingStatement;

    StatementMode(PreparingStatement preparingStatement) {
        this.preparingStatement = preparingStatement;
    }

    public PreparedStatement prepare(Connection connection, String sql) throws SQLException {
        return preparingStatement.prepare(connection, sql);
    }
}

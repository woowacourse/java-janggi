package janggi.database.utils;

import janggi.database.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class DatabaseUtils {

    private final DBConnector dbConnector;

    public DatabaseUtils(final DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public <T> T executeQuery(String query, Function<PreparedStatement, T> executor, Object... params) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            return executor.apply(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


package janggi.board.dao.utils;

import janggi.database.DBConnector;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtils {

    private final DBConnector dbConnector;

    public DatabaseUtils(final DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return dbConnector.getConnection().prepareStatement(query);
    }
}


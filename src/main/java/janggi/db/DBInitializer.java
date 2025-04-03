package janggi.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInitializer {
    private final DBConnection dbConnection;

    public DBInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean existDb() {
        try (Connection connection = dbConnection.getConnection();
             ResultSet resultSet = connection.prepareStatement("SHOW DATABASES LIKE 'janggi';").executeQuery()
        ) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        try (Connection connection = dbConnection.getConnection()) {
            connection.prepareStatement("DROP DATABASE IF EXISTS janggi;").executeUpdate();
            connection.prepareStatement("CREATE DATABASE IF NOT EXISTS janggi;").executeUpdate();
            connection.prepareStatement("USE janggi;").executeUpdate();
            connection.prepareStatement("CREATE TABLE turn(currentTeamColor VARCHAR(10));").executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE board(
                        rowIndex int,
                        columnIndex int,
                        teamColor VARCHAR(10),
                        pieceType VARCHAR(10)
                    );
                    """).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

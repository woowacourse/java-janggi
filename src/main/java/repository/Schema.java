package repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Schema {
    public static final String CREATE_TEAM = """
            CREATE TABLE IF NOT EXISTS TEAM (
                name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );
            
            INSERT IGNORE INTO TEAM (name) VALUES ('CHO'), ('HAN');
            
            """;

    public static final String CREATE_PIECE_TYPE = """
            CREATE TABLE IF NOT EXISTS PIECE_TYPE (
                name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );
            
            INSERT IGNORE INTO PIECE_TYPE (name) VALUES ('KING'), ('CHARIOT'), ('CANNON'), ('HORSE'), ('ELEPHANT'), ('SOLDIER'), ('HANPAWN'), ('CHOPAWN'), ('BLANK');
            
            """;

    public static final String CREATE_PIECE = """
            CREATE TABLE IF NOT EXISTS PIECE (
                 piece_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                 row_index INT NOT NULL,
                 column_index INT NOT NULL,
                 piece_type_name VARCHAR(20) NOT NULL,
                 team_name VARCHAR(20) NOT NULL,
                 UNIQUE(row_index, column_index),
            
                  FOREIGN KEY (piece_type_name) REFERENCES PIECE_TYPE(name)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE,
                  FOREIGN KEY (team_name) REFERENCES TEAM(name)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE
             );""";

    public static final String CREATE_TURN = """
            CREATE TABLE IF NOT EXISTS TURN (
                turn VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY,
                
                FOREIGN KEY (turn) REFERENCES TEAM(name)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE
            );""";

    public static final String CREATE_TABLE = CREATE_TEAM + CREATE_PIECE_TYPE
            + CREATE_PIECE + CREATE_TURN;

    public static void setTable(Connection connection) {
        try {
            final var statements = CREATE_TABLE.split(";");
            final var statement = connection.createStatement();

            for (final var singleQuery : statements) {
                statement.executeUpdate(singleQuery);
            }
            statement.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

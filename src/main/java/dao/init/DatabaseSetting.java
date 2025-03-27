package dao.init;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseSetting {

    public static final String INITIAL_TABLE_SETTING_QUERY = """
            CREATE TABLE IF NOT EXISTS team (
                name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );

            INSERT IGNORE INTO team (name) VALUES ('CHO'), ('HAN');

            CREATE TABLE IF NOT EXISTS piece_type (
                name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );

            INSERT IGNORE INTO piece_type (name) VALUES ('WANG'), ('SA'), ('CHA'), ('SANG'), ('MA'), ('PO'), ('BYEONG');

            CREATE TABLE IF NOT EXISTS game_room (
                name VARCHAR(100) PRIMARY KEY,
                turn VARCHAR(20) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS piece (
                 row_index INT NOT NULL,
                 column_index INT NOT NULL,
                 piece_type_name VARCHAR(20) NOT NULL,
                 team_name VARCHAR(20) NOT NULL,
                 game_room_name VARCHAR(100) NOT NULL,

                 PRIMARY KEY (row_index, column_index, game_room_name),

                 FOREIGN KEY (piece_type_name) REFERENCES piece_type(name)
                     ON UPDATE CASCADE
                     ON DELETE CASCADE,
                 FOREIGN KEY (team_name) REFERENCES team(name)
                     ON UPDATE CASCADE
                     ON DELETE CASCADE,
                 FOREIGN KEY (game_room_name) REFERENCES game_room(name)
                     ON UPDATE CASCADE
                     ON DELETE CASCADE
            );""";

    public static void settingTable(Connection connection) {
        try {
            final var statements = INITIAL_TABLE_SETTING_QUERY.split(";");
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

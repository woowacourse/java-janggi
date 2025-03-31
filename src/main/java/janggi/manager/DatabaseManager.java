package janggi.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {

    private static final String GAME_ROOM_TABLE = "GAME_ROOM";
    private static final String BOARD_TABLE = "BOARD";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    private final String server;
    private final String database;

    public DatabaseManager(String server, String database) {
        this.server = server;
        this.database = database;
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new IllegalArgumentException("Connection 중 예외 발생: ", e);
        }
    }

    public void createTableIfNotExist() {
        try (final Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(createGameRoomTable());
            stmt.executeUpdate(createPieceTable());
        } catch (final SQLException e) {
            throw new IllegalArgumentException("createTableIfNotExist 중 에러 발생", e);
        }
    }

    private String createGameRoomTable() {
        return createTableIfNotExist(GAME_ROOM_TABLE) +
                "ID INT PRIMARY KEY AUTO_INCREMENT,"+
                "NAME VARCHAR(10) UNIQUE," +
                "TURN VARCHAR(10) NOT NULL CHECK(TURN IN ('HAN', 'CHO')));";
    }

    private String createPieceTable() {
        return createTableIfNotExist(BOARD_TABLE) +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "PIECE_NAME VARCHAR(10) NOT NULL," +
                "TEAM VARCHAR(10) NOT NULL CHECK(TEAM IN('HAN', 'CHO'))," +
                "POSITION_ROW int NOT NULL," +
                "POSITION_COLUMN int NOT NULL," +
                "GAME_ROOM_NAME VARCHAR(30) NOT NULL," +
                "UNIQUE(GAME_ROOM_NAME, POSITION_ROW, POSITION_COLUMN)," +
                "FOREIGN KEY (GAME_ROOM_NAME) REFERENCES GAME_ROOM(NAME) ON DELETE CASCADE);";
    }

    private String createTableIfNotExist(String tableName) {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (";
    }
}

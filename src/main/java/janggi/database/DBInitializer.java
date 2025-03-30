package janggi.database;

import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private final DBConnector dbConnector;

    public DBInitializer(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void createTables() {
        try (Statement stmt = dbConnector.getConnection().createStatement()) {
            stmt.executeUpdate(createTeamTable());
            stmt.executeUpdate(createPiecesTable());
            stmt.executeUpdate(createTurnTable());
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] 테이블 생성 중 에러 발생");
        }
    }

    private String createTeamTable() {
        return "CREATE TABLE IF NOT EXISTS team (id INT AUTO_INCREMENT PRIMARY KEY, name CHAR(1) NOT NULL)";
    }

    private String createPiecesTable() {
        return "CREATE TABLE IF NOT EXISTS pieces ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "team_id INT NOT NULL, "
                + "piece_type CHAR(1) NOT NULL, "
                + "x INT NOT NULL, "
                + "y INT NOT NULL, "
                + "FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE"
                + ")";
    }

    private String createTurnTable() {
        return "CREATE TABLE IF NOT EXISTS turn ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "current_turn CHAR(1) NOT NULL)";
    }
}

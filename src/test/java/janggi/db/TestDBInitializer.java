package janggi.db;

import janggi.dao.BoardDao;
import janggi.model.BoardInitializer;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDBInitializer {
    private final DBConnection dbConnection;

    public TestDBInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void init() {
        try (Connection connection = dbConnection.getJanggiConnection()) {
            connection.prepareStatement("DROP TABLE IF EXISTS turn").executeUpdate();
            connection.prepareStatement("DROP TABLE IF EXISTS board").executeUpdate();
            connection.prepareStatement("CREATE TABLE turn(currentTeamColor VARCHAR(30))").executeUpdate();
            connection.prepareStatement("INSERT INTO turn(currentTeamColor) VALUES ('BLUE')").executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE board(
                        rowIndex int,
                        columnIndex int,
                        teamColor VARCHAR(30),
                        pieceType VARCHAR(30)
                    )
                    """).executeUpdate();
            BoardDao boardDao = new BoardDao(dbConnection);
            BoardInitializer boardInitializer = new BoardInitializer();
            boardDao.updateOccupiedPositions(boardInitializer.init().generateOccupiedPositions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

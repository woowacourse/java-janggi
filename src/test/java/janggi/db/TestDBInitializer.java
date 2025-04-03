package janggi.db;

import janggi.dao.BoardDao;
import janggi.model.GameInitializer;
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
            connection.prepareStatement("CREATE TABLE turn(currentTeamColor VARCHAR(5))").executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE board(
                        rowIndex int,
                        columnIndex int,
                        teamColor VARCHAR(30),
                        pieceType VARCHAR(30)
                    )
                    """).executeUpdate();
            BoardDao boardDao = new BoardDao(dbConnection);
            GameInitializer gameInitializer = new GameInitializer();
            boardDao.updateOccupiedPositions(gameInitializer.generateBoard());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

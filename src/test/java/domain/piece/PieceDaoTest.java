package domain.piece;

import database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private PieceDao pieceDao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
        pieceDao = new PieceDao(connection);
        connection.setAutoCommit(false);
    }

    @DisplayName("장기말 DB 저장 테스트")
    @Test
    public void test1() throws SQLException {
        // given player_id, type, position_column, position_row
        int playerId = 1;
        String type = "King";
        int Column = 5;
        int row = 2;

        // when
        pieceDao.insertPiece(playerId, type, Column, row);

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.rollback();
        resetAutoIncrement();
        connection.setAutoCommit(true);
        connection.close();
    }

    // AUTO_INCREMENT 값 리셋하는 메서드
    private void resetAutoIncrement() throws SQLException {
        String resetAutoIncrementSql = "ALTER TABLE player AUTO_INCREMENT = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(resetAutoIncrementSql)) {
            preparedStatement.executeUpdate();
        }
    }

}
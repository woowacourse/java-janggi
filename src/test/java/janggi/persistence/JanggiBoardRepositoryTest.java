package janggi.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardRepositoryTest {

    private Connection connection;
    private JanggiBoardRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.setAutoCommit(false);
        DatabaseInitializer.initialize(connection);
        repository = new JanggiBoardRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("초기 보드 상태를 저장하면 32개의 기물이 정상적으로 DB에 기록되어야 한다")
    void insertBoard() throws SQLException {
        insertDummyGame(1L);
        Board board = Board.initialize();

        repository.insertBoard(connection, 1L, board);

        verifyTotalPiecesCount(1L, 32);
    }

    @Test
    @DisplayName("보드 상태를 업데이트하면 변경된 특정 기물의 좌표가 DB에 정확히 반영되어야 한다")
    void updateBoard() throws SQLException {
        insertDummyGame(1L);
        insertDummyPiece(1L, "CHO", "PALACE", "0", 8, 4);

        repository.updateBoard(connection, 1L, createUpdatedBoard());

        verifyPieceLocation(1L, "CHO", "PALACE", "0", 7, 4);
    }

    private void insertDummyGame(long gameId) throws SQLException {
        String sql = "INSERT INTO GAME (game_id, cho_player_name, han_player_name, current_turn, is_finished) VALUES (?, '초', '한', 'CHO', FALSE)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void insertDummyPiece(long gameId, String side, String type, String num, int row, int col)
            throws SQLException {
        String sql = "INSERT INTO BOARD (game_id, side, piece_type, piece_number, row_index, column_index) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bindPieceParameters(statement, gameId, side, type, num, row, col);
            statement.executeUpdate();
        }
    }

    private void bindPieceParameters(PreparedStatement stmt, long gameId, String side, String type, String num, int row,
                                     int col) throws SQLException {
        stmt.setLong(1, gameId);
        stmt.setString(2, side);
        stmt.setString(3, type);
        stmt.setString(4, num);
        stmt.setInt(5, row);
        stmt.setInt(6, col);
    }

    private Board createUpdatedBoard() {
        Board board = Board.initialize();
        Destinations destinations = PieceType.PALACE.determineDestinations(new Position(8, 4), Side.CHO, board);
        board.movePiece(new Position(8, 4), new Position(7, 4), destinations);
        return board;
    }

    private void verifyTotalPiecesCount(long gameId, int expectedCount) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BOARD WHERE game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gameId);
            verifyCountResultSet(stmt, expectedCount);
        }
    }

    private void verifyCountResultSet(PreparedStatement stmt, int expectedCount) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getInt(1)).isEqualTo(expectedCount);
        }
    }

    private void verifyPieceLocation(long gameId, String side, String type, String num, int row, int col)
            throws SQLException {
        String sql = "SELECT row_index, column_index FROM BOARD WHERE game_id = ? AND side = ? AND piece_type = ? AND piece_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindIdentifierParameters(stmt, gameId, side, type, num);
            verifyLocationResultSet(stmt, row, col);
        }
    }

    private void bindIdentifierParameters(PreparedStatement stmt, long gameId, String side, String type, String num)
            throws SQLException {
        stmt.setLong(1, gameId);
        stmt.setString(2, side);
        stmt.setString(3, type);
        stmt.setString(4, num);
    }

    private void verifyLocationResultSet(PreparedStatement stmt, int expectedRow, int expectedCol) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            assertThat(rs.getInt("row_index")).isEqualTo(expectedRow);
            assertThat(rs.getInt("column_index")).isEqualTo(expectedCol);
        }
    }
}

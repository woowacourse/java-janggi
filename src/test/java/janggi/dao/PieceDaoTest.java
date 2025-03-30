package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private static Connection connection;
    private PieceDao pieceDao;

    @BeforeAll
    static void getConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:23306/test_janggi?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true",
                "user", "password"
        );
    }

    @BeforeEach
    void initTable() {
        pieceDao = new PieceDao();
        pieceDao.createTableIfAbsent(connection);
        pieceDao.clear(connection);
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @DisplayName("말을 저장한다.")
    @Test
    void saveData() {
        // given
        Map<Position, Piece> map = Map.of(new Position(1, 1), new King(Side.RED));
        Board board = new Board(map);

        // when
        pieceDao.save(board, connection);
        Map<Position, Piece> savedBoard = pieceDao.findAll(connection);

        // then
        assertThat(savedBoard).hasSize(1);
    }

    @DisplayName("특정 위치의 말을 조회한다.")
    @Test
    void findByPosition() {
        // given
        Position position = new Position(1, 1);
        Map<Position, Piece> map = Map.of(position, new King(Side.BLUE));
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        Piece found = pieceDao.findByPosition(position, connection);

        // then
        assertAll(() -> {
            assertThat(found).isInstanceOf(King.class);
            assertThat(found.getSide()).isEqualTo(Side.BLUE);
        });
    }

    @DisplayName("모든 말을 조회한다.")
    @Test
    void findAll() {
        // given
        Map<Position, Piece> map = Map.of(
                new Position(1, 1), new King(Side.BLUE),
                new Position(2, 2), new King(Side.RED)
        );
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        Map<Position, Piece> foundBoard = pieceDao.findAll(connection);

        // then
        assertThat(foundBoard).hasSize(2);
    }

    @DisplayName("특정 말을 다른 위치로 이동한다.")
    @Test
    void updateByPosition() {
        // given
        Position start = new Position(3, 3);
        Position end = new Position(4, 4);
        Piece piece = new King(Side.RED);
        Map<Position, Piece> map = Map.of(start, piece);
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        pieceDao.updateByPosition(piece, start, end, connection);

        // then
        Piece before = pieceDao.findByPosition(start, connection);
        Piece after = pieceDao.findByPosition(end, connection);

        assertAll(() -> {
            assertThat(before).isNull();
            assertThat(after).isNotNull();
        });
    }

    @DisplayName("특정 위치의 말을 삭제한다.")
    @Test
    void deleteByPosition() {
        // given
        Position position = new Position(5, 5);
        Map<Position, Piece> map = Map.of(position, new King(Side.BLUE));
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        pieceDao.deleteByPosition(position, connection);

        // then
        Piece deleted = pieceDao.findByPosition(position, connection);
        assertThat(deleted).isNull();
    }

    @DisplayName("DB에 말이 존재하는지 확인한다.")
    @Test
    void existsPieces() {
        // given
        Map<Position, Piece> map = Map.of(new Position(1, 1), new King(Side.RED));
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        boolean exists = pieceDao.existsPieces(connection);

        // then
        assertThat(exists).isTrue();
    }

    @DisplayName("말을 모두 삭제한다.")
    @Test
    void clear() {
        // given
        Map<Position, Piece> map = Map.of(new Position(1, 1), new King(Side.RED));
        Board board = new Board(map);
        pieceDao.save(board, connection);

        // when
        pieceDao.clear(connection);
        Map<Position, Piece> foundBoard = pieceDao.findAll(connection);

        // then
        assertThat(foundBoard).isEmpty();
    }
}

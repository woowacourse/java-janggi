package janggi.dao;

import janggi.piece.DefaultPiece;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BoardDaoTest {

    private BoardDao boardDao;

    @BeforeEach
    void beforeEach() {
        H2DatabaseConnector connector = new H2DatabaseConnector();
        Connection connection = connector.getConnection();
        boardDao = new BoardDao(connector);
        initializeSchema(connection);
    }

    @Test
    void saveAllBoardPieceTest() {
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(1, 9), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(3, 2), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(3, 8), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(4, 1), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 3), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 5), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 7), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 9), new DefaultPiece(Team.HAN, PieceType.SOLDIER));

        boardDao.saveAllBoardPiece(allPieces);

        Map<Position, Piece> findAllPieces = boardDao.findAllBoardPiece();

        assertThat(allPieces).isEqualTo(findAllPieces);
    }

    @Test
    void updateBoardPieceTest() {
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));

        boardDao.saveAllBoardPiece(allPieces);

        boardDao.updateBoardPiece(new Position(1, 1), new Position(1, 5));

        Map<Position, Piece> allBoardPiece = boardDao.findAllBoardPiece();

        assertThat(allBoardPiece.get(new Position(1, 5))).isEqualTo(new DefaultPiece(Team.HAN, PieceType.CHARIOT));
    }

    @Test
    void existsTrueTest() {
        Map<Position, Piece> allPieces = new HashMap<>();
        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        boardDao.saveAllBoardPiece(allPieces);
        assertThat(boardDao.existsBoardPiece()).isTrue();
    }

    @Test
    void existsFalseTest() {
        assertThat(boardDao.existsBoardPiece()).isFalse();
    }

    @Test
    void findAllBoardPieceTest() {
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(1, 9), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(3, 2), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(3, 8), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(4, 1), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 3), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 5), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 7), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 9), new DefaultPiece(Team.HAN, PieceType.SOLDIER));

        boardDao.saveAllBoardPiece(allPieces);

        Map<Position, Piece> findAllPieces = boardDao.findAllBoardPiece();

        assertThat(allPieces).isEqualTo(findAllPieces);
    }

    @Test
    void deletePieceByPositionTest() {
        Map<Position, Piece> allPieces = new HashMap<>();
        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        boardDao.saveAllBoardPiece(allPieces);
        assertAll(
                () -> assertThatCode(() -> boardDao.deletePieceByPosition(new Position(1, 1))).doesNotThrowAnyException(),
                () -> assertThat(boardDao.findAllBoardPiece()).doesNotContainKey(new Position(1, 1))
        );
    }

    @Test
    void deleteAllTest() {
        Map<Position, Piece> allPieces = new HashMap<>();
        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(1, 9), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(3, 2), new DefaultPiece(Team.HAN, PieceType.CANNON));

        boardDao.saveAllBoardPiece(allPieces);

        assertAll(
                () -> assertThatCode(() -> boardDao.deleteAll()).doesNotThrowAnyException(),
                () -> assertThat(boardDao.existsBoardPiece()).isFalse()
        );
    }

    private void initializeSchema(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                        CREATE TABLE board_piece (
                            board_piece_id INT PRIMARY KEY AUTO_INCREMENT,
                            piece_type     ENUM('KING', 'GUARD', 'HORSE', 'ELEPHANT', 'CANNON', 'CHARIOT', 'SOLDIER') NOT NULL,
                            team           ENUM('CHO', 'HAN') NOT NULL,
                            column_position INT NOT NULL,
                            row_position    INT NOT NULL
                        )
                    """);

            stmt.execute("""
                        CREATE TABLE turn (
                            turn ENUM('CHO', 'HAN') PRIMARY KEY
                        )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize schema", e);
        }
    }

}

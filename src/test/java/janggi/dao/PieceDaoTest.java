package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Piece;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final TestPieceDao testPieceDao = new TestPieceDao();

    @Test
    void connection() throws SQLException {
        try (Connection connection = testPieceDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    void addPiece() {
        Piece chariot = new Chariot(Camp.CHU);
        Point point = new Point(0, 0);
        testPieceDao.addPiece(chariot, point);
    }

    @Test
    void findPiece() {
        Point point = new Point(0, 0);
        Piece chariot = new Chariot(Camp.CHU);
        Piece piece = testPieceDao.findByPoint(point);
        assertThat(piece.getPieceType()).isEqualTo(chariot.getPieceType());
    }

    @Test
    void updatePiece() {
        Point point = new Point(0, 0);
        Piece cannon = new Cannon(Camp.HAN);
        testPieceDao.updatePieceByPoint(point, cannon);
        assertThat(testPieceDao.findByPoint(point).getPieceType()).isEqualTo(cannon.getPieceType());
    }

    @Test
    void deletePiece() {
        Point point = new Point(0, 0);
        testPieceDao.deletePieceByPoint(point);
        assertThat(testPieceDao.findByPoint(point)).isNull();
    }
}

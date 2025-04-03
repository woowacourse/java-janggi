package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.infra.DatabaseConfig;
import janggi.infra.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private DatabaseConfig DBConfig = new DatabaseConfig(
            "localhost:23306", "janggi_test",
            "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "test", "test");
    private DatabaseConnector DBConnector;
    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        DBConnector = new DatabaseConnector(DBConfig);
        pieceDao = new PieceDao(DBConnector);
    }

    @Test
    void connection() throws SQLException {
        try (Connection connection = DBConnector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    void addPiece() {
        Piece chariot = new Chariot(Camp.CHU);
        Point point = new Point(0, 0);
        pieceDao.addPiece(chariot, point);
    }

    @Test
    void findPiece() {
        Point point = new Point(0, 0);
        Piece chariot = new Chariot(Camp.CHU);
        Optional<Piece> piece = pieceDao.findByPoint(point);

        assertThat(piece)
                .isPresent()
                .get()
                .extracting(Piece::getPieceType)
                .isEqualTo(chariot.getPieceType());
    }

    @Test
    void updatePiece() {
        Point point = new Point(0, 0);
        Piece cannon = new Cannon(Camp.HAN);
        pieceDao.updatePieceByPoint(point, cannon);

        assertThat(pieceDao.findByPoint(point))
                .isPresent()
                .get()
                .extracting(Piece::getPieceType)
                .isEqualTo(cannon.getPieceType());
    }

    @Test
    void deletePiece() {
        Point point = new Point(0, 0);
        pieceDao.deletePieceByPoint(point);
        assertThat(pieceDao.findByPoint(point)).isNull();
    }
}

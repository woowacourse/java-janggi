package dao;

import static org.assertj.core.api.Assertions.assertThat;

import model.Team;
import model.piece.Cannon;
import model.piece.Piece;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final TestDaoConfiguration testdaoConfiguration = new TestDaoConfiguration();
    private final PieceDao pieceDao = new PieceDao(testdaoConfiguration);

    @AfterEach
    void clearData() {
        pieceDao.deletePieces();
    }

    @Test
    public void addPiece() {
        Position position = new Position(Column.ONE, Row.TWO);
        Piece piece = new Cannon(Team.RED);
        pieceDao.addPiece(position, piece);
    }

    @Test
    public void findPieceByPosition() {
        Position position = new Position(Column.TEN, Row.THREE);
        int pieceByPosition = pieceDao.findPieceByPosition(position);
        assertThat(pieceByPosition).isEqualTo(26);
    }

    @Test
    public void deleteAllPiece() {
        pieceDao.deletePieces();
    }

    /*
    @Test
    public void updatePiece() {
        Position departure = new Position(Column.SEVEN, Row.FIVE);
        Position arrival = new Position(Column.SIX, Row.FIVE);
        PieceDao pieceDao = new PieceDao();
        pieceDao.updatePiece(departure, arrival);
    }
     */
}

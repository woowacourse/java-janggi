package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import model.piece.PieceType;
import model.piece.Team;
import model.piece.Piece;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final TestDaoConfiguration testdaoConfiguration = new TestDaoConfiguration();
    private final PieceDao pieceDao = new PieceDao(testdaoConfiguration);

    @AfterEach
    void clearData() {
        pieceDao.deletePieces();
    }

    @DisplayName("피스를 저장할 수 있어야 한다.")
    @Test
    void addPiece() {
        Position position = new Position(Column.ONE, Row.TWO);
        Piece piece = new Piece(Team.RED, PieceType.CANNON);
        pieceDao.addPiece(position, piece);
        Map<Position, Piece> allPieces = pieceDao.getAllPieces();
        assertThat(allPieces.get(position)).isEqualTo(piece);
    }

    @DisplayName("모든 피스를 삭제할 수 있어야 한다.")
    @Test
    void deleteAllPiece() {
        //given
        Position position1 = new Position(Column.ONE, Row.TWO);
        Piece piece1 = new Piece(Team.RED, PieceType.CANNON);
        pieceDao.addPiece(position1, piece1);

        Position position2 = new Position(Column.TWO, Row.THREE);
        Piece piece2 = new Piece(Team.RED, PieceType.CANNON);
        pieceDao.addPiece(position2, piece2);

        //when
        pieceDao.deletePieces();

        //then
        assertThat(pieceDao.getAllPieces().size()).isEqualTo(0);
    }

    @DisplayName("특정 피스의 위치를 바꿀 수 있어야 한다.")
    @Test
    public void updatePiece() {
        //given
        Position position = new Position(Column.ONE, Row.TWO);
        Piece piece = new Piece(Team.RED, PieceType.CHARIOT);
        pieceDao.addPiece(position, piece);

        Position departure = new Position(Column.ONE, Row.TWO);
        Position arrival = new Position(Column.FIVE, Row.TWO);
        pieceDao.updatePiece(departure, arrival);
        Map<Position, Piece> allPieces = pieceDao.getAllPieces();
        assertThat(allPieces.get(arrival)).isEqualTo(piece);
        assertThat(allPieces.get(departure)).isNull();
    }
}

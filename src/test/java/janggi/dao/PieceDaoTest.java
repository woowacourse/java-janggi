package janggi.dao;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final PieceDao pieceDao = new PieceDao();

    @DisplayName("데이터베이스에 기물을 저장한다.")
    @Test
    void addPieceTest() {

        // given
        Piece piece = new Soldier(new Position(1, 1), RED);

        // when & then
        assertThatCode(() -> {
            pieceDao.addPiece(piece);
        }).doesNotThrowAnyException();
    }

    @DisplayName("데이터베이스에서 아이디로 기물을 찾는다.")
    @Test
    void findPieceByIdTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), RED);
        pieceDao.addPiece(soldier);
        Piece piece = pieceDao.findPieceById(1);

        assertThat(piece.getClass()).isEqualTo(Soldier.class);
    }

    @DisplayName("데이터베이스에 기물 모음을 저장한다.")
    @Test
    void addPiecesTest() {

        // given
        Piece cannon = new Cannon(new Position(1, 1), RED);
        Piece chariot = new Chariot(new Position(2, 2), RED);
        List<Piece> pieces = List.of(cannon, chariot);

        // when & then
        assertThatCode(() -> {
            pieceDao.addPieces(pieces);
        }).doesNotThrowAnyException();


    }

}

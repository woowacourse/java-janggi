package janggi.db;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.InitialElephantSetting;
import janggi.domain.PiecesInitializer;
import janggi.domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    Connection connection = new Connection();

    @Disabled
    @DisplayName("기물이 테이블에 제대로 들어가는지 확인합니다.")
    @Test
    void addPieceTest() {
        List<Piece> initialPieces = PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT)
                .getPieces();

        for (Piece initialPiece : initialPieces) {
            PieceDao pieceDao = new PieceDao(connection);
            pieceDao.addPiece(initialPiece);
        }
    }

    @Disabled
    @DisplayName("기물을 db에서 가져오는지 확인합니다.")
    @Test
    void readAllPieceTest() {
        List<Piece> expected = PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT)
                .getPieces();

        PieceDao pieceDao = new PieceDao(connection);
        List<Piece> initialPieces = pieceDao.readAllPiece();

        assertThat(initialPieces).isEqualTo(expected);
    }

    @Disabled
    @DisplayName("piece 테이블을 데이터를 전체 삭제하는지 확인합니다.")
    @Test
    void deleteAllPieceTest() {
        PieceDao pieceDao = new PieceDao(connection);
        pieceDao.deleteAllPiece();
    }
}

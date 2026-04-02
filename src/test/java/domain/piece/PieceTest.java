package domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PieceTest {

    @Test
    @DisplayName("장기에서 진영과 기물 종류에 맞는 기물을 생성한다.")
    void of_ReturnPiece_WhenCreateWithSideAndPieceType() {
        // given, when
        Piece piece = Piece.of(Side.CHO, PieceType.CANON);

        // then
        assertThat(piece.getSide()).isEqualTo(Side.CHO);
        assertThat(piece.getPieceType()).isEqualTo(PieceType.CANON);
    }
}

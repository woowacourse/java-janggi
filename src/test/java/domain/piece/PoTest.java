package domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {
    private static final Piece TEST_PO = PieceFactory.create(PieceType.PO, Team.CHO);
    private static final Piece TARGET_PO = PieceFactory.create(PieceType.PO, Team.HAN);

    @Test
    void 포가_포를_잡으면_예외가_발생해야_한다() {
        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(TARGET_PO)).isInstanceOf(IllegalArgumentException.class);
    }
}
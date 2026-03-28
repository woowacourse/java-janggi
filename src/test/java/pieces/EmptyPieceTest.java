package pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EmptyPieceTest {

    @Test
    void 비어있는_기물은_채워진_기물로_이용할_수_없다() {
        // given
        Piece emptyPiece = EmptyPiece.getInstance();
        // when & then
        assertThatThrownBy(emptyPiece::asFullPiece)
            .isInstanceOf(IllegalArgumentException.class);
    }
}
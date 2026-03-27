package pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import position.Position;

class EmptyPieceTest {
    @Test
    void 비어있는_기물은_이동이_불가능하다() {
        // given
        Position departure = new Position(1, 1);
        Position destination = new Position(1, 2);
        Piece emptyPiece = new EmptyPiece();
        // when & then
        assertThatThrownBy(() -> emptyPiece.askMoveContext(departure, destination))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
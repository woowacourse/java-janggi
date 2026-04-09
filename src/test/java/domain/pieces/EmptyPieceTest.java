package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.exception.NoPieceException;
import domain.pieces.exception.PieceErrorMessage;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class EmptyPieceTest {

    @Test
    void 비어있는_기물로_이동을_시도하면_예외를_던진다() {
        // given
        Position departure = new Position(1, 1);
        Position destination = new Position(1, 2);
        Piece emptyPiece = new EmptyPiece();
        // when & then
        assertThatThrownBy(() -> emptyPiece.askMoveContext(departure, destination))
                .isInstanceOf(NoPieceException.class)
                .hasMessage(PieceErrorMessage.NO_PIECE.message());
    }

    @Test
    void 비어있는기물은_비어있는_식별자를_반환한다() {
        // given
        Piece emptyPiece = new EmptyPiece();
        // when & then
        assertThat(emptyPiece.getType()).isEqualTo(PieceType.EMPTY);
    }
}

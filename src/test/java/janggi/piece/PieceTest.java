package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.direction.PieceType;
import janggi.position.Position;
import janggi.direction.PieceMoveRule;
import janggi.strategy.ObstacleBlockStrategy;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 같은_위치로_이동을_시도하는_경우_예외가_발생한다() {
        // Given
        final Position position1 = new Position(10, 1);
        final Piece piece = new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new ObstacleBlockStrategy()),
                position1);

        // When & Then
        assertThat(piece.isSamePosition(position1)).isTrue();
    }
}

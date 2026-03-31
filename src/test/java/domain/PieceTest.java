package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.strategy.HorseMoveStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    @DisplayName("기물은 이동 후에 이동한 위치에 맞는 이동 경로를 알아야 한다.")
    void piece_after_moveTo_test() {
        Position pre = new Position(4, 4);
        Piece piece = Piece.of(new PieceProperty(PieceType.HORSE, Team.RED), HorseMoveStrategy.of(pre));

        Position after = new Position(5, 6);
        piece.moveTo(after);

        assertThat(piece.canMoveTo(new Position(6, 8))).isTrue();
        assertThat(piece.canMoveTo(new Position(3, 2))).isFalse();
    }
}
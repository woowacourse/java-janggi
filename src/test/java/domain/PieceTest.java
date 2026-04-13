package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.strategy.HorseMoveStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    @DisplayName("기물은 기물 전략에 맞는 이동 규칙에 따라 이동할 수 있다.")
    void piece_after_canMoveTo_test() {
        Position current = new Position(3, 3);
        Position target = new Position(5, 2);
        Piece horsepiece = Piece.of(new PieceProperty(PieceType.HORSE, Team.RED), HorseMoveStrategy.getInstance());

        assertThat(horsepiece.canMoveTo(current, target)).isTrue();
    }
}

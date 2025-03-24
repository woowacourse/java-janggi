package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Position;
import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SingleMoveStrategyTest {

    @Test
    @DisplayName("총 이동 거리가 1일 경우 검증을 통과한다")
    void validate_passesWhenTotalDistanceIsOne() {
        // given
        MoveStrategy strategy = new SingleMoveStrategy();
        Position departure = Position.of(3, 3);
        Position destination = Position.of(3, 4); // column +1

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("총 이동 거리가 1이 아니면 예외가 발생한다")
    void validate_throwsWhenTotalDistanceIsNotOne() {
        // given
        MoveStrategy strategy = new SingleMoveStrategy();
        Position departure = Position.of(3, 3);
        Position destination = Position.of(3, 5); // column +2

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

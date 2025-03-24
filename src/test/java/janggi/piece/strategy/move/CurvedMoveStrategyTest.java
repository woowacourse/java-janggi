package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Position;
import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CurvedMoveStrategyTest {

    @Test
    @DisplayName("직선과 대각선 이동이 모두 0이면 예외가 발생한다")
    void constructor_throwsWhenBothZero() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new CurvedMoveStrategy(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("직선과 대각선으로 모두 움직여야합니다.");
    }

    @Test
    @DisplayName("이동 거리가 설정된 직선+대각선과 일치하면 검증을 통과한다")
    void validate_passesOnCorrectDistance() {
        // given
        MoveStrategy strategy = new CurvedMoveStrategy(2, 1);
        Position departure = Position.of(1, 1);
        Position destination = Position.of(4, 2); // + (직선2, 대각선1)

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("이동 거리가 설정과 일치하지 않으면 예외가 발생한다")
    void validate_throwsWhenDistanceMismatch() {
        // given
        MoveStrategy strategy = new CurvedMoveStrategy(2, 1);
        Position departure = Position.of(1, 1);
        Position destination = Position.of(3, 2); // + (직선1, 대각선1)

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

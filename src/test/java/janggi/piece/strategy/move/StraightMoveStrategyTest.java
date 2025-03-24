package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Position;
import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StraightMoveStrategyTest {

    @Test
    @DisplayName("수직 이동 시 검증을 통과한다")
    void validate_passesWhenMovingVertically() {
        // given
        MoveStrategy strategy = new StraightMoveStrategy();
        Position departure = Position.of(2, 5);
        Position destination = Position.of(5, 5); // column 동일, row만 변경

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("수평 이동 시 검증을 통과한다")
    void validate_passesWhenMovingHorizontally() {
        // given
        MoveStrategy strategy = new StraightMoveStrategy();
        Position departure = Position.of(4, 2);
        Position destination = Position.of(4, 7); // row 동일, column만 변경

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("대각선 이동 시 예외가 발생한다")
    void validate_throwsWhenMovingDiagonally() {
        // given
        MoveStrategy strategy = new StraightMoveStrategy();
        Position departure = Position.of(3, 3);
        Position destination = Position.of(5, 5); // row/column 모두 변경

        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

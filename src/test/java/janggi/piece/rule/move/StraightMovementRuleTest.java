package janggi.piece.rule.move;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.Pieces;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.StraightMovementRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StraightMovementRuleTest {

    @Test
    @DisplayName("수직 이동 시 검증을 통과한다")
    void validate_passesWhenMovingVertically() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Position departure = Position.of(2, 5);
        final Position destination = Position.of(5, 5); // column 동일, row만 변경

        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("수평 이동 시 검증을 통과한다")
    void validate_passesWhenMovingHorizontally() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Position departure = Position.of(4, 2);
        final Position destination = Position.of(4, 7); // row 동일, column만 변경

        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() -> strategy.validate(board, departure, destination));
    }

    @Test
    @DisplayName("직선이나 대각선 이동이 아니라면, 예외가 발생한다")
    void validate_throwsWhenIsNotStraightOrDiagonal() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Position departure = Position.of(3, 3);
        final Position destination = Position.of(6, 5);

        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }

    @Test
    @DisplayName("궁성 외부 대각선 이동 시 예외가 발생한다")
    void validate_throwsWhenMovingDiagonally() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Position departure = Position.of(3, 3);
        final Position destination = Position.of(5, 5); // row/column 모두 변경

        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }

    @DisplayName("궁성 내 수직 이동 시 검증을 통과한다")
    @Test
    void validate_passesWhenMovingVerticallyInPalace() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertAll(() -> {
            // 상궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(2, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(3, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 4), Position.of(3, 4)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 5), Position.of(2, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 5), Position.of(3, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(3, 5)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 6), Position.of(2, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 6), Position.of(3, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 6), Position.of(3, 6)));

            // 하궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(9, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(10, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 4), Position.of(10, 4)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 5), Position.of(9, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 5), Position.of(10, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(10, 5)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(9, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(10, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 6), Position.of(10, 6)));

            // 상궁 내부 -> 외부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(4, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 5), Position.of(4, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 6), Position.of(4, 6)));

            // 하궁 내부 -> 외부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(7, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 5), Position.of(7, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(7, 6)));

            // 외부 -> 상궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(4, 4), Position.of(3, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(4, 5), Position.of(3, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(4, 6), Position.of(3, 6)));

            // 외부 -> 하궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(7, 4), Position.of(8, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(7, 5), Position.of(8, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(7, 6), Position.of(8, 6)));
        });
    }

    @DisplayName("궁성 내 및 궁성 경계 수평 이동 시 검증을 통과한다")
    @Test
    void validate_passesWhenMovingHorizontallyInAndOutOfPalace() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertAll(() -> {
            // 상궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(1, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 5), Position.of(1, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(1, 6)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 4), Position.of(2, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(2, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 4), Position.of(2, 6)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(3, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 5), Position.of(3, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(3, 6)));

            // 하궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(8, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 5), Position.of(8, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(8, 6)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 4), Position.of(9, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(9, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 4), Position.of(9, 6)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 4), Position.of(10, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 5), Position.of(10, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 4), Position.of(10, 6)));

            // 상궁 내부 -> 외부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 6), Position.of(1, 7)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 6), Position.of(2, 7)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 6), Position.of(3, 7)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(1, 3)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 4), Position.of(2, 3)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(3, 3)));

            // 외부 -> 상궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 3), Position.of(1, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 3), Position.of(2, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 3), Position.of(3, 4)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 7), Position.of(1, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 7), Position.of(2, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 7), Position.of(3, 6)));

            // 하궁 내부 -> 외부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(8, 7)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 6), Position.of(9, 7)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 6), Position.of(10, 7)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(8, 3)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 4), Position.of(9, 3)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 4), Position.of(10, 3)));

            // 외부 -> 하궁 내부
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 3), Position.of(8, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 3), Position.of(9, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 3), Position.of(10, 4)));

            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 7), Position.of(8, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 7), Position.of(9, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 7), Position.of(10, 6)));
        });
    }

    @DisplayName("궁성 내부 대각선 이동 시 (중앙 포함) 검증을 통과한다")
    @Test
    void validate_passesWhenMovingDiagonallyInPalaceWithCenter() {
        // given
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        // when
        // then
        assertAll(() -> {
            // to 상궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(2, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 6), Position.of(2, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(2, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 6), Position.of(2, 5)));

            // withBlock 상궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(1, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(1, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(3, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(2, 5), Position.of(3, 6)));

            // pass 상궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 4), Position.of(3, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(1, 6), Position.of(3, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 4), Position.of(1, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(3, 6), Position.of(1, 4)));

            // to 하궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(9, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(9, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 4), Position.of(9, 5)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 6), Position.of(9, 5)));

            // withBlock 하궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(8, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(8, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(10, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(9, 5), Position.of(10, 6)));

            // pass 하궁 중앙
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 4), Position.of(10, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(8, 6), Position.of(10, 4)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 4), Position.of(8, 6)));
            assertDoesNotThrow(() -> strategy.validate(board, Position.of(10, 6), Position.of(8, 4)));
        });
    }

    @DisplayName("궁성 내 대각선 이동이지만 중앙을 지나지 않으면 예외 발생")
    @Test
    void validate_failsWhenDiagonalNotPassingThroughPalaceCenter() {
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        assertAll(() -> {
            assertThatThrownBy(() -> strategy.validate(board, Position.of(1, 5), Position.of(2, 4)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(1, 5), Position.of(2, 6)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(3, 5), Position.of(2, 4)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(3, 5), Position.of(2, 6)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(8, 5), Position.of(9, 4)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(8, 5), Position.of(9, 6)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(10, 5), Position.of(9, 4)))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> strategy.validate(board, Position.of(10, 5), Position.of(9, 6)))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @DisplayName("출발지가 궁성이기에 궁성 중앙을 통과하는 대각선 이동을 시도하지만, 도착지가 궁성 외부라면 예외 발생")
    @Test
    void validate_failsWhenDepartureIsPalaceButDestinationIsNotPalace() {
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        assertThatThrownBy(() -> strategy.validate(board, Position.of(10, 4), Position.of(7, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출발지와 도착지가 궁성이지만, 대각선 이동이 아니라면 예외 발생")
    @Test
    void validate_failsWhenDepartureIsPalaceButIsNotDiagonalMovement() {
        final MovementRule strategy = StraightMovementRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

        assertThatThrownBy(() -> strategy.validate(board, Position.of(10, 4), Position.of(9, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

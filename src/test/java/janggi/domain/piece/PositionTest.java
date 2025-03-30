package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.movement.Movement;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    /*
     * 한의 궁성 (column, row)
     * (3, 0) (4, 0) (5, 0)
     * (3, 1) (4, 1) (5, 1)
     * (3, 2) (4, 2) (5, 2)
     *
     * 초의 궁성 (column, row)
     * (3, 7) (4, 7) (5, 7)
     * (3, 8) (4, 8) (5, 8)
     * (3, 9) (4, 9) (5, 9)
     */

    @DisplayName("궁성에 해당하는 영역인지 판단할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void testIsPalace(Position position) {
        assertThat(position.isPalace()).isTrue();
    }

    @DisplayName("궁성에 해당하는 영역이 아닌지 판단할 수 있다.")
    @Test
    void testNotInPalace() {
        // given
        Position position = new Position(Column.ZERO, Row.ZERO);
        // when
        // then
        assertThat(position.isPalace()).isFalse();
    }

    @DisplayName("원하는 움직임을 적용하여 위치를 바꿀 수 있다.")
    @Test
    void testMovePosition() {
        // given
        Position position = new Position(Column.ZERO, Row.ZERO);
        // when
        Position movedPosition = position.move(Movement.RIGHT);
        // then
        assertThat(movedPosition).isEqualTo(new Position(Column.ONE, Row.ZERO));
    }

    @DisplayName("보드 밖으로 이동하려고 할 경우 예외가 발생한다.")
    @Test
    void testIllegalMovePosition() {
        // given
        Position position = new Position(Column.ZERO, Row.ZERO);
        // when
        // then
        assertThatThrownBy(() -> position.move(Movement.UP))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보드를 벗어나는 위치입니다.");
    }

    @DisplayName("보드 밖으로 이동할 수 있는지 확인할 수 있다.")
    @Test
    void testCanMovePosition() {
        // given
        Position position = new Position(Column.ZERO, Row.ZERO);
        // when
        boolean canMoveDown = position.canMove(Movement.DOWN);
        boolean canMoveUp = position.canMove(Movement.UP);
        boolean canMoveRight = position.canMove(Movement.RIGHT);
        boolean canMoveLeft = position.canMove(Movement.LEFT);
        boolean canMoveLeftDown = position.canMove(Movement.LEFT_DOWN);
        boolean canMoveLeftUp = position.canMove(Movement.LEFT_UP);
        boolean canMoveRightDown = position.canMove(Movement.RIGHT_DOWN);
        boolean canMoveRightUp = position.canMove(Movement.RIGHT_UP);
        // then
        assertAll(
                () -> assertThat(canMoveDown).isTrue(),
                () -> assertThat(canMoveUp).isFalse(),
                () -> assertThat(canMoveRight).isTrue(),
                () -> assertThat(canMoveLeft).isFalse(),
                () -> assertThat(canMoveLeftDown).isFalse(),
                () -> assertThat(canMoveLeftUp).isFalse(),
                () -> assertThat(canMoveRightDown).isTrue(),
                () -> assertThat(canMoveRightUp).isFalse()
        );
    }


    private static Stream<Arguments> testIsPalace() {
        return Stream.of(
                Arguments.of(new Position(Column.THREE, Row.ZERO)),
                Arguments.of(new Position(Column.FOUR, Row.ZERO)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO)),
                Arguments.of(new Position(Column.THREE, Row.ONE)),
                Arguments.of(new Position(Column.FOUR, Row.ONE)),
                Arguments.of(new Position(Column.FIVE, Row.ONE)),
                Arguments.of(new Position(Column.THREE, Row.TWO)),
                Arguments.of(new Position(Column.FOUR, Row.TWO)),
                Arguments.of(new Position(Column.FIVE, Row.TWO)),
                Arguments.of(new Position(Column.THREE, Row.SEVEN)),
                Arguments.of(new Position(Column.FOUR, Row.SEVEN)),
                Arguments.of(new Position(Column.FIVE, Row.SEVEN)),
                Arguments.of(new Position(Column.THREE, Row.EIGHT)),
                Arguments.of(new Position(Column.FOUR, Row.EIGHT)),
                Arguments.of(new Position(Column.FIVE, Row.EIGHT)),
                Arguments.of(new Position(Column.THREE, Row.NINE)),
                Arguments.of(new Position(Column.FOUR, Row.NINE)),
                Arguments.of(new Position(Column.FIVE, Row.NINE))
        );
    }
}

package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.LEFT_DOWN;
import static janggi.temp.Movement.LEFT_UP;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.RIGHT_DOWN;
import static janggi.temp.Movement.RIGHT_UP;
import static janggi.temp.Movement.UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("사(Guard) 테스트")
class GuardTest {

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

    @DisplayName("한궁의 사는 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동")
    @MethodSource("guardMovements")
    void testMoveHanGuard(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.ONE);
        Position destination = current.move(movement);
        Guard guardInCenter = new Guard(current, Team.HAN);
        // when
        Guard movedGuard = guardInCenter.move(destination);
        // then
        assertThat(movedGuard).isEqualTo(new Guard(destination, Team.HAN));
    }

    @DisplayName("초궁의 사는 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동")
    @MethodSource("guardMovements")
    void testMoveChoGuard(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.EIGHT);
        Position destination = current.move(movement);
        Guard guardInCenter = new Guard(current, Team.CHO);
        // when
        Guard movedGuard = guardInCenter.move(destination);
        // then
        assertThat(movedGuard).isEqualTo(new Guard(destination, Team.CHO));
    }

    private static Stream<Arguments> guardMovements() {
        return Stream.of(UP, DOWN, LEFT, RIGHT, RIGHT_DOWN, RIGHT_UP, LEFT_DOWN, LEFT_UP)
                .map(Arguments::of);
    }

    @DisplayName("사는 한 칸만 이동할 수 있다.")
    @Test
    void testMovingRuleValidation() {
        // given
        Guard guard = new Guard(new Position(Column.THREE, Row.ZERO), Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> guard.move(new Position(Column.FIVE, Row.TWO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
    @Test
    void testValidatePalaceDestination() {
        // given
        Guard guard = new Guard(new Position(Column.FOUR, Row.TWO), Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> guard.move(new Position(Column.FOUR, Row.THREE)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁성 밖으로 이동할 수 없습니다.");
    }

    @DisplayName("사는 궁성의 가운데가 아니고, 모서리가 아닌 위치에서는 대각선으로 이동할 수 없다.")
    @Test
    void testInvalidMoveInPalaceSides() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        Position destination = new Position(Column.THREE, Row.ONE);
        Guard guard = new Guard(current, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> guard.move(destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("사는 자기 위치로 이동할 수 없다.")
    @Test
    void testMoveToCurrentPosition() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        Guard guard = new Guard(current, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> guard.move(current))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
    }

    // TODO 같은 팀이 있는 위치로 이동할 수 없다
}

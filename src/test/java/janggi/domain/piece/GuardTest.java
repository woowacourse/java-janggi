package janggi.domain.piece;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.LEFT_DOWN;
import static janggi.domain.movement.Movement.LEFT_UP;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.RIGHT_DOWN;
import static janggi.domain.movement.Movement.RIGHT_UP;
import static janggi.domain.movement.Movement.UP;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;
import janggi.domain.piece.Guard;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.Map;
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
        Guard guardInCenter = new Guard(Team.HAN);
        // when
        // then
        assertThatCode(
                () -> guardInCenter.validateMove(current, destination, new Board(Map.of(current, guardInCenter))))
                .doesNotThrowAnyException();
    }

    @DisplayName("초궁의 사는 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동")
    @MethodSource("guardMovements")
    void testMoveChoGuard(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.EIGHT);
        Position destination = current.move(movement);
        Guard guardInCenter = new Guard(Team.CHO);
        // when
        // then
        assertThatCode(
                () -> guardInCenter.validateMove(current, destination, new Board(Map.of(current, guardInCenter))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> guardMovements() {
        return Stream.of(UP, DOWN, LEFT, RIGHT, RIGHT_DOWN, RIGHT_UP, LEFT_DOWN, LEFT_UP)
                .map(Arguments::of);
    }

    @DisplayName("사는 한 칸만 이동할 수 있다.")
    @Test
    void testMovingRuleValidation() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Guard guard = new Guard(Team.HAN);
        Position destination = new Position(Column.FIVE, Row.TWO);
        // when
        // then
        assertThatThrownBy(() -> guard.validateMove(current, destination, new Board(Map.of(current, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
    @Test
    void testValidatePalaceDestination() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        Guard guard = new Guard(Team.HAN);
        Position destination = new Position(Column.FOUR, Row.THREE);
        // when
        // then
        assertThatThrownBy(() -> guard.validateMove(current, destination, new Board(Map.of(current, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁성 밖으로 이동할 수 없습니다.");
    }

    @DisplayName("사는 궁성의 가운데가 아니고, 모서리가 아닌 위치에서는 대각선으로 이동할 수 없다.")
    @Test
    void testInvalidMoveInPalaceSides() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        Position destination = new Position(Column.THREE, Row.ONE);
        Guard guard = new Guard(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> guard.validateMove(current, destination, new Board(Map.of(current, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }
}

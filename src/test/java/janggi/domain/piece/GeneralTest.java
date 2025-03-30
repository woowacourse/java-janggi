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
import janggi.domain.piece.General;
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

@DisplayName("궁(General) 테스트")
class GeneralTest {

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

    @DisplayName("한궁의 궁은 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동")
    @MethodSource("generalMovements")
    void testMoveHanGeneral(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.ONE);
        Position destination = current.move(movement);
        General generalInCenter = new General(Team.HAN);
        // when
        // then
        assertThatCode(
                () -> generalInCenter.validateMove(current, destination, new Board(Map.of(current, generalInCenter))))
                .doesNotThrowAnyException();
    }

    @DisplayName("초궁의 궁은 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동")
    @MethodSource("generalMovements")
    void testMoveChoGeneral(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.EIGHT);
        Position destination = current.move(movement);
        General generalInCenter = new General(Team.CHO);
        // when
        // then
        assertThatCode(
                () -> generalInCenter.validateMove(current, destination, new Board(Map.of(current, generalInCenter))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> generalMovements() {
        return Stream.of(UP, DOWN, LEFT, RIGHT, RIGHT_DOWN, RIGHT_UP, LEFT_DOWN, LEFT_UP)
                .map(Arguments::of);
    }

    @DisplayName("한 칸만 이동할 수 있다.")
    @Test
    void testMovingRuleValidation() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        General general = new General(Team.HAN);
        Position destination = new Position(Column.FIVE, Row.TWO);
        // when
        // then
        assertThatThrownBy(() -> general.validateMove(current, destination,
                new Board(Map.of(current, general))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("궁성 밖으로 이동할 수 없다.")
    @Test
    void testValidatePalaceDestination() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        General general = new General(Team.HAN);
        Position destination = new Position(Column.FOUR, Row.THREE);
        // when
        // then
        assertThatThrownBy(() -> general.validateMove(current, destination,
                new Board(Map.of(current, general))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁성 밖으로 이동할 수 없습니다.");
    }

    @DisplayName("궁성의 가운데가 아니고, 모서리가 아닌 위치에서는 대각선으로 이동할 수 없다.")
    @Test
    void testInvalidMoveInPalaceSides() {
        // given
        Position current = new Position(Column.FOUR, Row.TWO);
        Position destination = new Position(Column.THREE, Row.ONE);
        General general = new General(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> general.validateMove(current, destination,
                new Board(Map.of(current, general))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> testMoveHanGeneral() {
        return Stream.of(
                Arguments.of(new Position(Column.THREE, Row.ZERO)),
                Arguments.of(new Position(Column.FOUR, Row.ZERO)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO)),
                Arguments.of(new Position(Column.THREE, Row.ONE)),
                Arguments.of(new Position(Column.FIVE, Row.ONE)),
                Arguments.of(new Position(Column.THREE, Row.TWO)),
                Arguments.of(new Position(Column.FOUR, Row.TWO)),
                Arguments.of(new Position(Column.FIVE, Row.TWO))
        );
    }

    private static Stream<Arguments> testMoveChoGeneral() {
        return Stream.of(
                Arguments.of(new Position(Column.THREE, Row.SEVEN)),
                Arguments.of(new Position(Column.FOUR, Row.SEVEN)),
                Arguments.of(new Position(Column.FIVE, Row.SEVEN)),
                Arguments.of(new Position(Column.THREE, Row.EIGHT)),
                Arguments.of(new Position(Column.FIVE, Row.EIGHT)),
                Arguments.of(new Position(Column.THREE, Row.NINE)),
                Arguments.of(new Position(Column.FOUR, Row.NINE)),
                Arguments.of(new Position(Column.FIVE, Row.NINE))
        );
    }
}

package janggi.temp;

import static janggi.temp.movement.Movement.DOWN;
import static janggi.temp.movement.Movement.LEFT;
import static janggi.temp.movement.Movement.LEFT_DOWN;
import static janggi.temp.movement.Movement.LEFT_UP;
import static janggi.temp.movement.Movement.RIGHT;
import static janggi.temp.movement.Movement.RIGHT_DOWN;
import static janggi.temp.movement.Movement.RIGHT_UP;
import static janggi.temp.movement.Movement.UP;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.temp.game.Board;
import janggi.temp.game.Team;
import janggi.temp.movement.Movement;
import janggi.temp.piece.Elephant;
import janggi.temp.position.Column;
import janggi.temp.position.Position;
import janggi.temp.position.Row;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("상(Elephant) 테스트")
class ElephantTest {

    private static final List<List<Movement>> legalMovements = List.of(
            List.of(UP, UP, LEFT_UP),
            List.of(UP, UP, RIGHT_UP),
            List.of(DOWN, DOWN, LEFT_DOWN),
            List.of(DOWN, DOWN, RIGHT_DOWN),
            List.of(LEFT, LEFT, LEFT_UP),
            List.of(LEFT, LEFT, LEFT_DOWN),
            List.of(RIGHT, RIGHT, RIGHT_UP),
            List.of(RIGHT, RIGHT, RIGHT_DOWN)
    );

    @DisplayName("직선으로 두 칸, 45도 대각선으로 한 칸 이동할 수 있다.")
    @ParameterizedTest(name = "{0}-{1}-{2} 이동 테스트")
    @MethodSource("elephantMovements")
    void testElephantMovements(Movement first, Movement second, Movement third) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(first).move(second).move(third);
        Elephant elephant = new Elephant(Team.HAN);
        // when
        // then
        assertThatCode(() -> elephant.validateMove(current, destination, new Board(Map.of(current, elephant))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> elephantMovements() {
        return legalMovements.stream()
                .map(movements -> Arguments.of(movements.getFirst(), movements.get(1), movements.get(2)));
    }

    @DisplayName("이동 규칙에 맞지 않으면 이동할 수 없다.")
    @ParameterizedTest(name = "{0} 이동 불가 테스트")
    @MethodSource("elephantIllegalMovements")
    void testElephantValidateMovement(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Elephant elephant = new Elephant(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> elephant.validateMove(current, destination, new Board(Map.of(current, elephant))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> elephantIllegalMovements() {
        return Arrays.stream(Movement.values())
                .map(Arguments::of);
    }

    @DisplayName("이동 경로 첫 번째 위치에 다른 기물이 있을 경우 이동할 수 없다.")
    @ParameterizedTest(name = "{0}-{1}-{2} 이동 테스트")
    @MethodSource("elephantMovements")
    void testBlockedFirstMovement(Movement first, Movement second, Movement third) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position firstMoved = current.move(first);
        Position destination = current.move(first).move(second).move(third);
        Elephant blockedElephant = new Elephant(Team.HAN);
        Elephant moveingElephant = new Elephant(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> moveingElephant.validateMove(current, destination,
                new Board(Map.of(current, blockedElephant, firstMoved, blockedElephant))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }

    @DisplayName("이동 경로 두 번째 위치에 다른 기물이 있을 경우 이동할 수 없다.")
    @ParameterizedTest(name = "{0}-{1}-{2} 이동 테스트")
    @MethodSource("elephantMovements")
    void testBlockedSecondMovement(Movement first, Movement second, Movement third) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position secondMoved = current.move(first).move(second);
        Position destination = secondMoved.move(third);
        Elephant blockedElephant = new Elephant(Team.HAN);
        Elephant moveingElephant = new Elephant(Team.HAN);
        // when
        // then
        assertThatThrownBy(
                () -> moveingElephant.validateMove(current, destination,
                        new Board(Map.of(current, blockedElephant, secondMoved, blockedElephant))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }
}

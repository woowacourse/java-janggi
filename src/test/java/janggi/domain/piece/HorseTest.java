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
import janggi.domain.piece.Horse;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("마(Horse) 테스트")
class HorseTest {

    private static final List<List<Movement>> legalMovements = List.of(
            List.of(UP, LEFT_UP),
            List.of(UP, RIGHT_UP),
            List.of(DOWN, LEFT_DOWN),
            List.of(DOWN, RIGHT_DOWN),
            List.of(LEFT, LEFT_UP),
            List.of(LEFT, LEFT_DOWN),
            List.of(RIGHT, RIGHT_UP),
            List.of(RIGHT, RIGHT_DOWN)
    );

    @DisplayName("직선으로 한 칸, 45도 대각선으로 한 칸 이동할 수 있다.")
    @ParameterizedTest(name = "{0}-{1} 이동 테스트")
    @MethodSource("horseMovements")
    void testHorseMovements(Movement first, Movement second) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(first).move(second);
        Horse horse = new Horse(Team.HAN);
        // when
        // then
        assertThatCode(() -> horse.validateMove(current, destination, new Board(Map.of(current, horse))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> horseMovements() {
        return legalMovements.stream()
                .map(movements -> Arguments.of(movements.getFirst(), movements.get(1)));
    }

    @DisplayName("이동 규칙에 맞지 않으면 이동할 수 없다.")
    @ParameterizedTest(name = "{0} 이동 불가 테스트")
    @MethodSource("horseIllegalMovements")
    void testHorseValidateMovement(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Horse horse = new Horse(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> horse.validateMove(current, destination, new Board(Map.of(current, horse))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> horseIllegalMovements() {
        return Arrays.stream(Movement.values())
                .map(Arguments::of);
    }

    @DisplayName("이동 경로 첫 번째 위치에 다른 기물이 있을 경우 이동할 수 없다.")
    @ParameterizedTest(name = "{0}-{1} 이동 테스트")
    @MethodSource("horseMovements")
    void testBlockedMovement(Movement first, Movement second) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position firstMoved = current.move(first);
        Position destination = current.move(first).move(second);
        Horse horse = new Horse(Team.HAN);
        // when
        // then
        assertThatThrownBy(
                () -> horse.validateMove(current, destination, new Board(Map.of(current, horse, firstMoved, horse))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }
}

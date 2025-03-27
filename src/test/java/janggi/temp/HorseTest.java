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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("마(Horse) 테스트")
class HorseTest {

    /*
    (0,0) (1,0) (2,0) (3,0) (4,0) (5,0) (6,0) (7,0) (8,0)
    (0,1) (1,1) (2,1) (3,1) (4,1) (5,1) (6,1) (7,1) (8,1)
    (0,2) (1,2) (2,2) (3,2) (4,2) (5,2) (6,2) (7,2) (8,2)
    (0,3) (1,3) (2,3) (3,3) (4,3) (5,3) (6,3) (7,3) (8,3)
    (0,4) (1,4) (2,4) (3,4) (4,4) (5,4) (6,4) (7,4) (8,4)
    (0,5) (1,5) (2,5) (3,5) (4,5) (5,5) (6,5) (7,5) (8,5)
    (0,6) (1,6) (2,6) (3,6) (4,6) (5,6) (6,6) (7,6) (8,6)
    (0,7) (1,7) (2,7) (3,7) (4,7) (5,7) (6,7) (7,7) (8,7)
    (0,8) (1,8) (2,8) (3,8) (4,8) (5,8) (6,8) (7,8) (8,8)
    (0,9) (1,9) (2,9) (3,9) (4,9) (5,9) (6,9) (7,9) (8,9)
    */

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
        Horse horse = new Horse(current, Team.HAN);
        // when
        Piece moved = horse.move(destination, Set.of(horse));
        // then
        assertThat(moved).isEqualTo(new Horse(destination, Team.HAN));
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
        Horse horse = new Horse(current, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> horse.move(destination, Set.of(horse)))
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
        Horse movingHorse = new Horse(current, Team.HAN);
        Horse blockedHorse = new Horse(firstMoved, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingHorse.move(destination, Set.of(movingHorse, blockedHorse)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }
}

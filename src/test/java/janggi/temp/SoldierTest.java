package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.RIGHT;
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

class SoldierTest {

    private static final List<Movement> hanLegalMovements = List.of(DOWN, RIGHT, LEFT);
    private static final List<Movement> choLegalMovements = List.of(UP, RIGHT, LEFT);

    @DisplayName("한나라의 졸은 아래로 한 칸 또는 옆으로 한 칸 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동 테스트")
    @MethodSource("hanMovements")
    void testHanMovements(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Soldier soldier = new Soldier(current, Team.HAN);
        // when
        Piece moved = soldier.move(destination, Set.of(soldier));
        // then
        assertThat(moved).isEqualTo(new Soldier(destination, Team.HAN));
    }

    private static Stream<Arguments> hanMovements() {
        return hanLegalMovements.stream()
                .map(Arguments::of);
    }

    @DisplayName("초나라의 병은 위로 한 칸 또는 옆으로 한 칸 이동할 수 있다.")
    @ParameterizedTest(name = "{0} 이동 테스트")
    @MethodSource("choMovements")
    void testChoMovements(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Soldier soldier = new Soldier(current, Team.CHO);
        // when
        Piece moved = soldier.move(destination, Set.of(soldier));
        // then
        assertThat(moved).isEqualTo(new Soldier(destination, Team.CHO));
    }

    private static Stream<Arguments> choMovements() {
        return choLegalMovements.stream()
                .map(Arguments::of);
    }

    @DisplayName("한의 이동 규칙에 맞지 않으면 이동할 수 없다.")
    @ParameterizedTest(name = "{0} 이동 불가 테스트")
    @MethodSource("hanIllegalMovements")
    void testHanValidateMovement(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Soldier soldier = new Soldier(current, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> soldier.move(destination, Set.of(soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> hanIllegalMovements() {
        return Arrays.stream(Movement.values())
                .filter(movement -> !hanLegalMovements.contains(movement))
                .map(Arguments::of);
    }

    @DisplayName("초의 이동 규칙에 맞지 않으면 이동할 수 없다.")
    @ParameterizedTest(name = "{0} 이동 불가 테스트")
    @MethodSource("choIllegalMovements")
    void testChoValidateMovement(Movement movement) {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Position destination = current.move(movement);
        Soldier soldier = new Soldier(current, Team.CHO);
        // when
        // then
        assertThatThrownBy(() -> soldier.move(destination, Set.of(soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> choIllegalMovements() {
        return Arrays.stream(Movement.values())
                .filter(movement -> !choLegalMovements.contains(movement))
                .map(Arguments::of);
    }

//    @DisplayName("자기 위치로 이동할 수 없다.")
//    @Test
//    void testMoveToCurrentPosition() {
//        // given
//        Position current = new Position(Column.FOUR, Row.FOUR);
//        Soldier soldier = new Soldier(current, Team.HAN);
//        // when
//        // then
//        assertThatThrownBy(() -> soldier.move(current, Set.of(soldier)))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
//    }

//    @DisplayName("같은 팀이 있는 위치로 이동할 수 없다.")
//    @Test
//    void testValidateMoveSameTeamPosition() {
//        // given
//        Team team = Team.HAN;
//        Position current = new Position(Column.FOUR, Row.FOUR);
//        Position destination = new Position(Column.FOUR, Row.THREE);
//        Soldier movingSoldier = new Soldier(current, team);
//        Soldier hanSoldier = new Soldier(destination, team);
//        // when
//        // then
//        assertThatThrownBy(() -> movingSoldier.move(destination, Set.of(hanSoldier, movingSoldier)))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("[ERROR] 같은 팀이 있는 위치로 이동할 수 없습니다.");
//    }
}

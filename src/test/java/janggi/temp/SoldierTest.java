package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

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
        Soldier moved = soldier.move(destination);
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
        Soldier moved = soldier.move(destination);
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
        assertThatThrownBy(() -> soldier.move(destination))
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
        assertThatThrownBy(() -> soldier.move(destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> choIllegalMovements() {
        return Arrays.stream(Movement.values())
                .filter(movement -> !choLegalMovements.contains(movement))
                .map(Arguments::of);
    }

    @DisplayName("자기 위치로 이동할 수 없다.")
    @Test
    void testMoveToCurrentPosition() {
        // given
        Position current = new Position(Column.FOUR, Row.FOUR);
        Soldier soldier = new Soldier(current, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> soldier.move(current))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
    }

    // TODO 같은 팀이 있으면 이동할 수 없다.
}

package janggi.domain.piece;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.UP;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;
import janggi.domain.piece.Soldier;
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

@DisplayName("병(Soldier) 테스트")
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
        Soldier soldier = new Soldier(Team.HAN);
        // when
        // then
        assertThatCode(() -> soldier.validateMove(current, destination, new Board(Map.of(current, soldier))))
                .doesNotThrowAnyException();
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
        Soldier soldier = new Soldier(Team.CHO);
        // when
        // then
        assertThatCode(() -> soldier.validateMove(current, destination, new Board(Map.of(current, soldier))))
                .doesNotThrowAnyException();
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
        Soldier soldier = new Soldier(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> soldier.validateMove(current, destination, new Board(Map.of(current, soldier))))
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
        Soldier soldier = new Soldier(Team.CHO);
        // when
        // then
        assertThatThrownBy(() -> soldier.validateMove(current, destination, new Board(Map.of(current, soldier))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private static Stream<Arguments> choIllegalMovements() {
        return Arrays.stream(Movement.values())
                .filter(movement -> !choLegalMovements.contains(movement))
                .map(Arguments::of);
    }
}

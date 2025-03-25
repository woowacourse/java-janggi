package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoTest {

    static final Position START_POSITION = new Position(4, 4);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position jumpPadPosition, Position destination) {
        Po po = new Po(START_POSITION);
        Jol jumpPad = new Jol(jumpPadPosition, CampType.CHO);

        Piece movedPo = po.move(destination, List.of(), List.of(jumpPad));

        assertThat(movedPo.getPosition()).isEqualTo(destination);
    }

    static Stream<Arguments> canMove() {
        return Stream.of(
                Arguments.of(
                        new Position(START_POSITION.x() + 1, START_POSITION.y()),
                        new Position(8, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x() - 1, START_POSITION.y()),
                        new Position(0, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() + 1),
                        new Position(START_POSITION.x(), 9)),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() - 1),
                        new Position(START_POSITION.x(), 0))
        );
    }

    @DisplayName("목적지가 일직선 상에 없는 경우 이동할 수 없다.")
    @Test
    void canNotMoveBecauseRuleOfMove() {
        Po po = new Po(START_POSITION);
        Position destination = new Position(5, 7);

        assertThatThrownBy(() -> po.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("장기말의 경로상에 점프대가 없는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveWithoutJumpPad(Position destination) {
        Po po = new Po(START_POSITION);

        assertThatThrownBy(() -> po.move(destination, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveWithoutJumpPad() {
        return Stream.of(
                Arguments.of(new Position(8, START_POSITION.y())),
                Arguments.of(new Position(0, START_POSITION.y())),
                Arguments.of(new Position(START_POSITION.x(), 0)),
                Arguments.of(new Position(START_POSITION.x(), 9))
        );
    }

    @DisplayName("장기말의 경로상에 아군 포가 있는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseAlliesPo(Position otherPoPosition, Position destination) {
        Po po = new Po(START_POSITION);
        Po alliesPoInPath = new Po(otherPoPosition);

        assertThatThrownBy(() -> po.move(destination, List.of(), List.of(alliesPoInPath)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseAlliesPo() {
        return Stream.of(
                Arguments.of(
                        new Position(START_POSITION.x() + 1, START_POSITION.y()),
                        new Position(8, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x() - 1, START_POSITION.y()),
                        new Position(0, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() + 1),
                        new Position(START_POSITION.x(), 0)),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() - 1),
                        new Position(START_POSITION.x(), 9))
        );
    }

    @DisplayName("장기말의 경로상에 적군 포가 있는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseEnemyPo(Position otherPoPosition, Position destination) {
        Po po = new Po(START_POSITION);
        Po enemyPoInPath = new Po(otherPoPosition);

        assertThatThrownBy(() -> po.move(destination, List.of(enemyPoInPath), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseEnemyPo() {
        return Stream.of(
                Arguments.of(
                        new Position(START_POSITION.x() + 1, START_POSITION.y()),
                        new Position(8, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x() - 1, START_POSITION.y()),
                        new Position(0, START_POSITION.y())),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() + 1),
                        new Position(START_POSITION.x(), 0)),
                Arguments.of(
                        new Position(START_POSITION.x(), START_POSITION.y() - 1),
                        new Position(START_POSITION.x(), 9))
        );
    }


    @DisplayName("장기말의 경로상에 적군과 아군 상관없이 점프대가 2개이상인 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveBecauseTwoJumpPad(List<Position> jumpPadPositions, Position destination) {
        Po po = new Po(START_POSITION);
        Piece enemyJumpPad = new Jol(jumpPadPositions.getFirst(), CampType.CHO);
        Piece alliesJumpPad = new Jol(jumpPadPositions.getLast(), CampType.CHO);

        assertThatThrownBy(() -> po.move(destination, List.of(enemyJumpPad), List.of(alliesJumpPad)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    static Stream<Arguments> canNotMoveBecauseTwoJumpPad() {
        return Stream.of(
                Arguments.of(
                        List.of(new Position(START_POSITION.x() + 1, START_POSITION.y()),
                                new Position(START_POSITION.x() + 2, START_POSITION.y())),
                        new Position(8, START_POSITION.y())),
                Arguments.of(
                        List.of(new Position(START_POSITION.x() - 1, START_POSITION.y()),
                                new Position(START_POSITION.x() - 2, START_POSITION.y())),
                        new Position(0, START_POSITION.y())),
                Arguments.of(
                        List.of(new Position(START_POSITION.x(), START_POSITION.y() + 1),
                                new Position(START_POSITION.x(), START_POSITION.y() + 2)),
                        new Position(START_POSITION.x(), 0)),
                Arguments.of(
                        List.of(new Position(START_POSITION.x(), START_POSITION.y() - 1),
                                new Position(START_POSITION.x(), START_POSITION.y() - 2)),
                        new Position(START_POSITION.x(), 9))
        );
    }

    @DisplayName("목적지에 아군 장기말이 있는 경우 이동할 수 없다")
    @Test
    void canNotMoveBecauseAlliesInDestination() {
        Position jumpPadPosition = new Position(START_POSITION.x() + 1, START_POSITION.y());
        Position destination = new Position(START_POSITION.x() + 3, START_POSITION.y());
        Po po = new Po(START_POSITION);
        Jol jumpPad = new Jol(jumpPadPosition, CampType.CHO);
        Jol alliesInDestination = new Jol(destination, CampType.CHO);

        assertThatThrownBy(() -> po.move(destination, List.of(jumpPad), List.of(alliesInDestination)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("목적지에 상대 장기말이 있는 경우 이동할 수 없다")
    @Test
    void canMoveWithEnemyInDestination() {
        Position jumpPadPosition = new Position(START_POSITION.x() + 1, START_POSITION.y());
        Position destination = new Position(START_POSITION.x() + 3, START_POSITION.y());
        Po po = new Po(START_POSITION);
        Jol jumpPad = new Jol(jumpPadPosition, CampType.CHO);
        Jol enemyInDestination = new Jol(destination, CampType.CHO);

        Piece movedPo = po.move(destination, List.of(enemyInDestination), List.of(jumpPad));

        assertThat(movedPo.getPosition()).isEqualTo(destination);
    }
}
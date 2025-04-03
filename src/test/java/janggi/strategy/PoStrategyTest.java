package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.data.fixture.GungsungPositionFixture;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PoStrategyTest {

    static final Position START_POSITION = new Position(4, 4);
    PoStrategy strategy = new PoStrategy();

    @DisplayName("동서남북 방향으로 끝까지 이동 가능하다")
    @ParameterizedTest
    @MethodSource()
    void canMove(Position jumpPadPosition, Position destination) {
        Piece jumpPad = new Piece(PieceType.JOL, jumpPadPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of(jumpPad));
        assertThat(canMove).isTrue();
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

    @DisplayName("궁성 내에서는 동서남북에 추가로 대각선 이동도 가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canMoveInGungsung(Position start, Position destination) {
        Piece jumpPad = new Piece(PieceType.JOL, GungsungPositionFixture.CENTER);
        boolean canMove = strategy.ableToMove(start, destination, List.of(), List.of(jumpPad));
        assertThat(canMove).isTrue();
    }

    static Stream<Arguments> canMoveInGungsung() {
        return Stream.of(
                Arguments.of(GungsungPositionFixture.DOWN, GungsungPositionFixture.UP),
                Arguments.of(GungsungPositionFixture.UP, GungsungPositionFixture.DOWN),
                Arguments.of(GungsungPositionFixture.LEFT, GungsungPositionFixture.RIGHT),
                Arguments.of(GungsungPositionFixture.RIGHT, GungsungPositionFixture.LEFT),
                Arguments.of(GungsungPositionFixture.DOWN_RIGHT, GungsungPositionFixture.UP_LEFT),
                Arguments.of(GungsungPositionFixture.DOWN_LEFT, GungsungPositionFixture.UP_RIGHT),
                Arguments.of(GungsungPositionFixture.UP_RIGHT, GungsungPositionFixture.DOWN_LEFT),
                Arguments.of(GungsungPositionFixture.UP_LEFT, GungsungPositionFixture.DOWN_RIGHT)
        );
    }

    @DisplayName("궁성 영역 밖에서는 대각선 이동이 불가능하다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveOutOfGungsung(Position jumpPadPosition, Position destination) {
        Piece jumpPad = new Piece(PieceType.JOL, jumpPadPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of(jumpPad));
        assertThat(canMove).isFalse();
    }

    static Stream<Arguments> canNotMoveOutOfGungsung() {
        return Stream.of(
                Arguments.of(
                        new Position(START_POSITION.x() + 1, START_POSITION.y() + 1),
                        new Position(START_POSITION.x() + 2, START_POSITION.y() + 2)),
                Arguments.of(
                        new Position(START_POSITION.x() + 1, START_POSITION.y() - 1),
                        new Position(START_POSITION.x() + 2, START_POSITION.y() - 2)),
                Arguments.of(
                        new Position(START_POSITION.x() - 1, START_POSITION.y() + 1),
                        new Position(START_POSITION.x() - 2, START_POSITION.y() + 2)),
                Arguments.of(
                        new Position(START_POSITION.x() - 1, START_POSITION.y() - 1),
                        new Position(START_POSITION.x() - 2, START_POSITION.y() - 2))
        );
    }

    @DisplayName("장기말의 경로상에 점프대가 없는 경우 이동할 수 없다")
    @ParameterizedTest
    @MethodSource()
    void canNotMoveWithoutJumpPad(Position destination) {
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of());
        assertThat(canMove).isFalse();
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
        Piece otherPo = new Piece(PieceType.PO, otherPoPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(), List.of(otherPo));
        assertThat(canMove).isFalse();
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
        Piece otherPo = new Piece(PieceType.PO, otherPoPosition);
        boolean canMove = strategy.ableToMove(START_POSITION, destination, List.of(otherPo), List.of());
        assertThat(canMove).isFalse();
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
        Piece enemyJumpPad = new Piece(PieceType.JOL, jumpPadPositions.getFirst());
        Piece alliesJumpPad = new Piece(PieceType.JOL, jumpPadPositions.getLast());
        boolean canMove = strategy.ableToMove(
                START_POSITION, destination, List.of(enemyJumpPad), List.of(alliesJumpPad));
        assertThat(canMove).isFalse();
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

        Piece jumpPad = new Piece(PieceType.JOL, jumpPadPosition);
        Piece alliesInDestination = new Piece(PieceType.JOL, destination);

        boolean canMove = strategy.ableToMove(
                START_POSITION, destination, List.of(jumpPad), List.of(alliesInDestination));
        assertThat(canMove).isFalse();
    }

    @DisplayName("목적지에 상대 장기말이 있는 경우 이동할 수 있다")
    @Test
    void canMoveWithEnemyInDestination() {
        Position jumpPadPosition = new Position(START_POSITION.x() + 1, START_POSITION.y());
        Position destination = new Position(START_POSITION.x() + 3, START_POSITION.y());

        Piece jumpPad = new Piece(PieceType.JOL, jumpPadPosition);
        Piece enemyInDestination = new Piece(PieceType.JOL, destination);

        boolean canMove = strategy.ableToMove(
                START_POSITION, destination, List.of(enemyInDestination), List.of(jumpPad));
        assertThat(canMove).isTrue();
    }
}
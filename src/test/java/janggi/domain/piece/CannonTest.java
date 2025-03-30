package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("포(Cannon) 테스트")
class CannonTest {

    @DisplayName("포는 포를 잡을 수 없다.")
    @Test
    void testPhoTakePho() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking = new Position(Column.FOUR, Row.ZERO);
        Position destination = new Position(Column.FIVE, Row.ZERO);
        Cannon hanCannon = new Cannon(Team.HAN);
        Cannon choCannon = new Cannon(Team.CHO);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, hanCannon, blocking, soldier, destination, choCannon));
        // when
        // then
        assertThatThrownBy(() -> hanCannon.validateMove(current, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("하나의 기물을 뛰어넘어서 오른쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}열로 이동")
    @MethodSource
    void testMoveRight(Column destinationColumn) {
        // given
        Position current = new Position(Column.ZERO, Row.ZERO);
        Position blocking = new Position(Column.ONE, Row.ZERO);
        Position destination = new Position(destinationColumn, Row.ZERO);
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, cannon, blocking, soldier, destination, soldier));
        // when
        // then
        assertThatCode(() -> cannon.validateMove(current, destination, board))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveRight() {
        return Arrays.stream(Column.values())
                .skip(2)
                .map(Arguments::of);
    }

    @DisplayName("하나의 기물을 뛰어넘어서 왼쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}열로 이동")
    @MethodSource
    void testMoveLeft(Column destinationColumn) {
        // given
        Position current = new Position(Column.EIGHT, Row.ZERO);
        Position blocking = new Position(Column.SEVEN, Row.ZERO);
        Position destination = new Position(destinationColumn, Row.ZERO);
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, cannon, blocking, soldier, destination, soldier));
        // when
        // then
        assertThatCode(() -> cannon.validateMove(current, destination, board))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveLeft() {
        return Arrays.stream(Column.values())
                .limit(7)
                .map(Arguments::of);
    }

    @DisplayName("하나의 기물을 뛰어넘어서 위쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}행으로 이동")
    @MethodSource
    void testMoveUp(Row destinationRow) {
        // given
        Position current = new Position(Column.ZERO, Row.NINE);
        Position blocking = new Position(Column.ZERO, Row.EIGHT);
        Position destination = new Position(Column.ZERO, destinationRow);
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, cannon, blocking, soldier, destination, soldier));
        // when
        // then
        assertThatCode(() -> cannon.validateMove(current, destination, board))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveUp() {
        return Arrays.stream(Row.values())
                .limit(8)
                .map(Arguments::of);
    }

    @DisplayName("하나의 기물을 뛰어넘어서 아래 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}행으로 이동")
    @MethodSource
    void testMoveDown(Row destinationRow) {
        // given
        Position current = new Position(Column.ZERO, Row.ZERO);
        Position blocking = new Position(Column.ZERO, Row.ONE);
        Position destination = new Position(Column.ZERO, destinationRow);
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, cannon, blocking, soldier, destination, soldier));
        // when
        // then
        assertThatCode(() -> cannon.validateMove(current, destination, board))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveDown() {
        return Arrays.stream(Row.values())
                .skip(2)
                .map(Arguments::of);
    }

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

    @DisplayName("가운데에 하나의 기물이 존재할 때 궁성의 대각선을 따라 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource
    void testMoveDiagonal(Position current, Position destination) {
        // given
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.CHO);
        Board board = new Board(Map.of(current, cannon, new Position(Column.FOUR, Row.ONE), soldier,
                new Position(Column.FOUR, Row.EIGHT), soldier));
        // when
        // then
        assertThatCode(() -> cannon.validateMove(current, destination, board))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveDiagonal() {
        return Stream.of(
                // 한궁
                Arguments.of(new Position(Column.THREE, Row.ZERO), new Position(Column.FIVE, Row.TWO)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO), new Position(Column.THREE, Row.TWO)),
                Arguments.of(new Position(Column.THREE, Row.TWO), new Position(Column.FIVE, Row.ZERO)),
                Arguments.of(new Position(Column.FIVE, Row.TWO), new Position(Column.THREE, Row.ZERO)),
                // 초궁
                Arguments.of(new Position(Column.THREE, Row.SEVEN), new Position(Column.FIVE, Row.NINE)),
                Arguments.of(new Position(Column.FIVE, Row.SEVEN), new Position(Column.THREE, Row.NINE)),
                Arguments.of(new Position(Column.THREE, Row.NINE), new Position(Column.FIVE, Row.SEVEN)),
                Arguments.of(new Position(Column.FIVE, Row.NINE), new Position(Column.THREE, Row.SEVEN)));
    }

    @DisplayName("궁성이 아닌 위치에서는 대각선 경로로 이동할 수 없다.")
    @Test
    void testInvalidMove() {
        // given
        Position current = new Position(Column.ZERO, Row.ZERO);
        Position destination = new Position(Column.ONE, Row.ONE);
        Cannon cannon = new Cannon(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(current, destination, new Board(Map.of(current, cannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("직선으로 이동할 때 다른 기물을 뛰어넘지 않고 이동할 수 없다.")
    @Test
    void testNonBlockingPiece() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position destination = new Position(Column.FIVE, Row.ZERO);
        Cannon movingCannon = new Cannon(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingCannon.validateMove(current, destination,
                new Board(Map.of(current, movingCannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 다른 기물을 뛰어 넘어서 이동해야 합니다.");
    }

    @DisplayName("직선으로 이동할 때 2개 이상의 기물을 뛰어넘어서 이동할 수 없다.")
    @Test
    void testBlockingPiece() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking1 = new Position(Column.FOUR, Row.ZERO);
        Position blocking2 = new Position(Column.FIVE, Row.ZERO);
        Position destination = new Position(Column.SIX, Row.ZERO);
        Cannon cannon = new Cannon(Team.HAN);
        Soldier soldier = new Soldier(Team.HAN);
        Board board = new Board(Map.of(current, cannon, blocking1, soldier, blocking2, soldier));
        // when
        // then
        assertThatThrownBy(() -> cannon.validateMove(current, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @DisplayName("직선으로 이동할 때 포를 뛰어넘어서 이동할 수 없다.")
    @Test
    void testBlockingPho() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking = new Position(Column.FOUR, Row.ZERO);
        Position destination = new Position(Column.FIVE, Row.ZERO);
        Cannon movingCannon = new Cannon(Team.HAN);
        Cannon blockingCannon = new Cannon(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingCannon.validateMove(current, destination,
                new Board(Map.of(current, movingCannon, blocking, blockingCannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 넘을 수 없습니다.");
    }

    @DisplayName("궁성에서 대각선으로 이동할 때 포를 뛰어넘어서 이동할 수 없다.")
    @Test
    void test7() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking = new Position(Column.FOUR, Row.ONE);
        Position destination = new Position(Column.FIVE, Row.TWO);
        Cannon movingCannon = new Cannon(Team.HAN);
        Cannon blockingCannon = new Cannon(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingCannon.validateMove(current, destination,
                new Board(Map.of(current, movingCannon, blocking, blockingCannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 넘을 수 없습니다.");
    }
}

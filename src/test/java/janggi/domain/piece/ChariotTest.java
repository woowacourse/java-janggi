package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.piece.Chariot;
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

@DisplayName("차(Chariot) 테스트")
class ChariotTest {

    @DisplayName("오른쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}열로 이동")
    @MethodSource
    void testMoveRight(Column destinationColumn) {
        // given
        Position current = new Position(Column.ZERO, Row.ZERO);
        Position destination = new Position(destinationColumn, Row.ZERO);
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatCode(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveRight() {
        return Arrays.stream(Column.values())
                .skip(1)
                .map(Arguments::of);
    }

    @DisplayName("왼쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}열로 이동")
    @MethodSource
    void testMoveLeft(Column destinationColumn) {
        // given
        Position current = new Position(Column.EIGHT, Row.ZERO);
        Position destination = new Position(destinationColumn, Row.ZERO);
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatCode(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveLeft() {
        return Arrays.stream(Column.values())
                .limit(8)
                .map(Arguments::of);
    }

    @DisplayName("위쪽 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}행으로 이동")
    @MethodSource
    void testMoveUp(Row destinationRow) {
        // given
        Position current = new Position(Column.ZERO, Row.NINE);
        Position destination = new Position(Column.ZERO, destinationRow);
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatCode(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveUp() {
        return Arrays.stream(Row.values())
                .limit(9)
                .map(Arguments::of);
    }

    @DisplayName("아래 직선으로 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest(name = "{0}행으로 이동")
    @MethodSource
    void testMoveDown(Row destinationRow) {
        // given
        Position current = new Position(Column.ZERO, Row.ZERO);
        Position destination = new Position(Column.ZERO, destinationRow);
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatCode(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveDown() {
        return Arrays.stream(Row.values())
                .skip(1)
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

    @DisplayName("궁성의 대각선을 따라 원하는 만큼 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource
    void testMoveDiagonal(Position current, Position destination) {
        // given
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatCode(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> testMoveDiagonal() {
        return Stream.of(
                // 한궁
                Arguments.of(new Position(Column.THREE, Row.ZERO), new Position(Column.FIVE, Row.TWO)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO), new Position(Column.THREE, Row.TWO)),
                Arguments.of(new Position(Column.THREE, Row.TWO), new Position(Column.FIVE, Row.ZERO)),
                Arguments.of(new Position(Column.FIVE, Row.TWO), new Position(Column.THREE, Row.ZERO)),
                Arguments.of(new Position(Column.THREE, Row.ZERO), new Position(Column.FOUR, Row.ONE)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO), new Position(Column.FOUR, Row.ONE)),
                Arguments.of(new Position(Column.THREE, Row.TWO), new Position(Column.FOUR, Row.ONE)),
                Arguments.of(new Position(Column.FIVE, Row.TWO), new Position(Column.FOUR, Row.ONE)),
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
        Chariot chariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> chariot.validateMove(current, destination, new Board(Map.of(current, chariot))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("직선으로 이동할 때 다른 기물을 뛰어넘어서 이동할 수 없다.")
    @Test
    void testBlockingPiece() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking = new Position(Column.FOUR, Row.ZERO);
        Position destination = new Position(Column.FIVE, Row.ZERO);
        Chariot movingChariot = new Chariot(Team.HAN);
        Chariot blockingChariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingChariot.validateMove(current, destination,
                new Board(Map.of(current, movingChariot, blocking, blockingChariot))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }

    @DisplayName("궁성에서 대각선으로 이동할 때 다른 기물을 뛰어넘어서 이동할 수 없다.")
    @Test
    void test7() {
        // given
        Position current = new Position(Column.THREE, Row.ZERO);
        Position blocking = new Position(Column.FOUR, Row.ONE);
        Position destination = new Position(Column.FIVE, Row.TWO);
        Chariot movingChariot = new Chariot(Team.HAN);
        Chariot blockingChariot = new Chariot(Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> movingChariot.validateMove(current, destination,
                new Board(Map.of(current, movingChariot, blocking, blockingChariot))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
    }
}

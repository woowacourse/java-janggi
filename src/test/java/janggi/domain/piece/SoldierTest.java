package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 0, RED", "0, 1, RED", "0,-1, RED", "0, -1, GREEN", "0, 1, GREEN", "-1, 0, GREEN"})
    @DisplayName("졸/병은 적진을 향한 수직/수평으로 1칸 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection, Team team) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = new Soldier(team);
        Board board = new Board(Map.of(position, soldier));
        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> soldier.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 0, RED", "1, 1, RED", "1, -1, RED", "0, 1, RED", "0,-1, RED",
            "-1, 0, GREEN", "-1, 1, GREEN", "-1, -1, GREEN", "0, -1, GREEN", "0, 1, GREEN"})
    @DisplayName("졸/병이 궁성 내부에 위치하면 적진을 향한 수직/수평/대각선으로 1칸 이동할 수 있다")
    void checkCanMoveInPalace(int rowDirection, int columnDirection, Team team) {
        // given
        Position position = Position.of(2, 5);
        Piece soldier = new Soldier(team);
        Board board = new Board(Map.of(position, soldier));
        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> soldier.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"RED, -1", "GREEN, 1"})
    @DisplayName("졸/병은 본진 방향으로 이동할 수 없다")
    void cannotCheckCanMoveToAllyBase(Team team, int rowDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = new Soldier(team);
        Board board = new Board(Map.of(position, soldier));
        Position movedPosition = position.adjust(rowDirection, 0);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> soldier.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("본진을 향할 수 없습니다");
    }

    private static Stream<Arguments> provideInvalidPositionOfDiagonal() {
        return Stream.of(Arguments.of(Position.of(3, 5), Position.of(2, 4), Team.GREEN),
                Arguments.of(Position.of(3, 5), Position.of(2, 6), Team.GREEN),
                Arguments.of(Position.of(2, 4), Position.of(1, 5), Team.GREEN),
                Arguments.of(Position.of(2, 6), Position.of(1, 5), Team.GREEN),
                Arguments.of(Position.of(8, 5), Position.of(9, 4), Team.RED),
                Arguments.of(Position.of(8, 5), Position.of(9, 6), Team.RED),
                Arguments.of(Position.of(9, 4), Position.of(10, 5), Team.RED),
                Arguments.of(Position.of(9, 6), Position.of(10, 5), Team.RED));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPositionOfDiagonal")
    @DisplayName("졸/병은 궁성 조건에 맞지 않는 이동을 할 수 없다")
    void checkCanNotMoveInvalidPalaceCondition(Position departure, Position destination, Team team) {
        // given
        Piece soldier = new Soldier(team);
        Board board = new Board(Map.of(departure, soldier));
        Placement placement = new Placement(board, departure, destination);

        // when
        // then
        assertThatThrownBy(() -> soldier.checkCanMove(placement, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수직/수평으로 1칸만 이동 가능합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 0", "3, 0", "1, 1", "2, 2"})
    @DisplayName("졸/병은 2칸 이상 움직일 수 없다")
    void cannotCheckCanMoveToInvalidCount(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = new Soldier(Team.RED);
        Board board = new Board(Map.of(position, soldier));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> soldier.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수직/수평으로 1칸만 이동 가능합니다.");
    }
}

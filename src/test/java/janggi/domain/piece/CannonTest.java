package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포는 수직/수평으로 포가 아닌 기물 하나를 넘고 보드판 내부를 자유롭게 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Piece soldier1 = new Soldier(Team.RED);
        Piece soldier2 = new Soldier(Team.RED);
        Piece soldier3 = new Soldier(Team.RED);
        Piece soldier4 = new Soldier(Team.RED);
        Board board = new Board(Map.of(position, cannon,
                position.adjust(1, 0), soldier1,
                position.adjust(0, 1), soldier2,
                position.adjust(-1, 0), soldier3,
                position.adjust(0, -1), soldier4)
        );

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> cannon.checkCanMove(placement, position, movedPosition));
    }

    private static Stream<Arguments> providePositionOfDiagonal() {
        return Stream.of(Arguments.of(Position.of(1, 4), Position.of(3, 6)),
                Arguments.of(Position.of(1, 6), Position.of(3, 4)),
                Arguments.of(Position.of(3, 4), Position.of(1, 6)),
                Arguments.of(Position.of(3, 6), Position.of(1, 4)));
    }

    @ParameterizedTest
    @MethodSource("providePositionOfDiagonal")
    @DisplayName("포가 궁성 내부에 위치하면 포가 아닌 기물 하나를 넘고 대각선으로 이동할 수 있다")
    void checkCanMoveDiagonalInPalace(Position departure, Position destination) {
        // given
        Position position = Position.of(2, 5);
        Piece cannon = new Cannon(Team.RED);
        Piece soldier = new Soldier(Team.RED);
        Board board = new Board(Map.of(departure, cannon, position, soldier));

        Placement placement = new Placement(board, departure, destination);

        // when
        // then
        assertDoesNotThrow(() -> cannon.checkCanMove(placement, departure, destination));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("포는 규칙에 어긋나게 움직일 수 없다")
    void cannotCheckCanMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Board board = new Board(Map.of(position, cannon));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> cannon.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수직/수평 방향 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("포는 기물 하나를 넘지 않으면 이동할 수 없다")
    void cannotCheckCanMoveWhenNotExistOtherPieceInRoute() {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Board board = new Board(Map.of(position, cannon));

        Position movedPosition = position.adjust(3, 0);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> cannon.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물이 1개 존재해야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포의 경로에 포가 포함되면 이동할 수 없다")
    void cannotCheckCanMoveWhenExistCannonInRoute(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Piece otherAllyCannon1 = new Cannon(Team.RED);
        Piece otherAllyCannon2 = new Cannon(Team.RED);
        Piece otherEnemyCannon1 = new Cannon(Team.GREEN);
        Piece otherEnemyCannon2 = new Cannon(Team.GREEN);
        Board board = new Board(Map.of(position, cannon,
                position.adjust(1, 0), otherAllyCannon1,
                position.adjust(-1, 0), otherAllyCannon2,
                position.adjust(0, 1), otherEnemyCannon1,
                position.adjust(0, -1), otherEnemyCannon2));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> cannon.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"3,0", "-3,0", "0,3", "0,-3"})
    @DisplayName("포의 경로에 1개 초과의 기물이 존재하면 이동할 수 없다")
    void cannotCheckCanMoveWhenExistOtherPieceMoreThanOneInRoute(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Piece soldier1 = new Soldier(Team.GREEN);
        Piece soldier2 = new Soldier(Team.GREEN);
        Piece soldier3 = new Soldier(Team.GREEN);
        Piece soldier4 = new Soldier(Team.GREEN);
        Piece soldier5 = new Soldier(Team.GREEN);
        Piece soldier6 = new Soldier(Team.GREEN);
        Piece soldier7 = new Soldier(Team.GREEN);
        Piece soldier8 = new Soldier(Team.GREEN);
        Board board = new Board(Map.of(position, cannon,
                position.adjust(1, 0), soldier1,
                position.adjust(2, 0), soldier2,
                position.adjust(-1, 0), soldier3,
                position.adjust(-2, 0), soldier4,
                position.adjust(0, 1), soldier5,
                position.adjust(0, 2), soldier6,
                position.adjust(0, -1), soldier7,
                position.adjust(0, -2), soldier8));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> cannon.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물이 1개 존재해야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "-2,0", "0,2", "0,-2"})
    @DisplayName("포는 포를 잡을 수 없다")
    void cannotCheckCanMoveWhenExistCannonInDestination(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(Team.RED);
        Piece soldier1 = new Soldier(Team.GREEN);
        Piece soldier2 = new Soldier(Team.GREEN);
        Piece soldier3 = new Soldier(Team.GREEN);
        Piece soldier4 = new Soldier(Team.GREEN);

        Piece cannon1 = new Cannon(Team.GREEN);
        Piece cannon2 = new Cannon(Team.GREEN);
        Piece cannon3 = new Cannon(Team.GREEN);
        Piece cannon4 = new Cannon(Team.GREEN);

        Board board = new Board(Map.of(position, cannon,
                position.adjust(1, 0), soldier1,
                position.adjust(-1, 0), soldier2,
                position.adjust(0, 1), soldier3,
                position.adjust(0, -1), soldier4,
                position.adjust(2, 0), cannon1,
                position.adjust(-2, 0), cannon2,
                position.adjust(0, 2), cannon3,
                position.adjust(0, -2), cannon4));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> cannon.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
    }
}

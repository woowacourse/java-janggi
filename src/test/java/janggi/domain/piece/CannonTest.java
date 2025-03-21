package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포는 수직/수평으로 포가 아닌 기물 하나를 넘고 보드판 내부를 자유롭게 이동할 수 있다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Piece soldier1 = new Soldier(position.adjust(1, 0), Team.RED);
        Piece soldier2 = new Soldier(position.adjust(0, 1), Team.RED);
        Piece soldier3 = new Soldier(position.adjust(-1, 0), Team.RED);
        Piece soldier4 = new Soldier(position.adjust(0, -1), Team.RED);
        Board board = Board.initialize(List.of(cannon, soldier1, soldier2, soldier3, soldier4));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        Piece move = cannon.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("포는 규칙에 어긋나게 움직일 수 없다")
    void cannotMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Board board = Board.initialize(List.of(cannon));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }

    @Test
    @DisplayName("포는 기물 하나를 넘지 않으면 이동할 수 없다")
    void cannotMoveWhenNotExistOtherPieceInRoute() {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Board board = Board.initialize(List.of(cannon));

        Position movedPosition = position.adjust(3, 0);

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포의 경로에 포가 포함되면 이동할 수 없다")
    void cannotMoveWhenExistCannonInRoute(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Piece otherAllyCannon1 = new Cannon(position.adjust(1, 0), Team.RED);
        Piece otherAllyCannon2 = new Cannon(position.adjust(-1, 0), Team.RED);
        Piece otherEnemyCannon1 = new Cannon(position.adjust(0, 1), Team.GREEN);
        Piece otherEnemyCannon2 = new Cannon(position.adjust(0, -1), Team.GREEN);
        Board board = Board.initialize(
                List.of(cannon, otherAllyCannon1, otherAllyCannon2, otherEnemyCannon1, otherEnemyCannon2));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 포가 존재합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"3,0", "-3,0", "0,3", "0,-3"})
    @DisplayName("포의 경로에 1개 초과의 기물이 존재하면 이동할 수 없다")
    void cannotMoveWhenExistOtherPieceMoreThanOneInRoute(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Piece soldier1 = new Soldier(position.adjust(1, 0), Team.GREEN);
        Piece soldier2 = new Soldier(position.adjust(2, 0), Team.GREEN);
        Piece soldier3 = new Soldier(position.adjust(-1, 0), Team.GREEN);
        Piece soldier4 = new Soldier(position.adjust(-2, 0), Team.GREEN);
        Piece soldier5 = new Soldier(position.adjust(0, 1), Team.GREEN);
        Piece soldier6 = new Soldier(position.adjust(0, 2), Team.GREEN);
        Piece soldier7 = new Soldier(position.adjust(0, -1), Team.GREEN);
        Piece soldier8 = new Soldier(position.adjust(0, -2), Team.GREEN);
        Board board = Board.initialize(
                List.of(cannon, soldier1, soldier2, soldier3, soldier4, soldier5, soldier6, soldier7, soldier8));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "-2,0", "0,2", "0,-2"})
    @DisplayName("포는 포를 잡을 수 없다")
    void cannotMoveWhenExistCannonInDestination(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece cannon = new Cannon(position, Team.RED);
        Piece soldier1 = new Soldier(position.adjust(1, 0), Team.GREEN);
        Piece soldier2 = new Soldier(position.adjust(-1, 0), Team.GREEN);
        Piece soldier3 = new Soldier(position.adjust(0, 1), Team.GREEN);
        Piece soldier4 = new Soldier(position.adjust(0, -1), Team.GREEN);

        Piece cannon1 = new Cannon(position.adjust(2, 0), Team.GREEN);
        Piece cannon2 = new Cannon(position.adjust(-2, 0), Team.GREEN);
        Piece cannon3 = new Cannon(position.adjust(0, 2), Team.GREEN);
        Piece cannon4 = new Cannon(position.adjust(0, -2), Team.GREEN);

        Board board = Board.initialize(
                List.of(cannon, soldier1, soldier2, soldier3, soldier4, cannon1, cannon2, cannon3, cannon4));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
    }
}

package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CannonTest {

    @Test
    @DisplayName("2개의 포가 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        final List<Cannon> ChoCannons = Cannon.defaultsOf(Team.CHO);
        final List<Cannon> HanCannons = Cannon.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoCannons.get(0).position).isEqualTo(Position.of(Team.decideRow(3, Team.CHO), 2));
            assertThat(ChoCannons.get(1).position).isEqualTo(Position.of(Team.decideRow(3, Team.CHO), 8));
            assertThat(HanCannons.get(0).position).isEqualTo(Position.of(Team.decideRow(3, Team.HAN), 2));
            assertThat(HanCannons.get(1).position).isEqualTo(Position.of(Team.decideRow(3, Team.HAN), 8));
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포는 수직/수평으로 포가 아닌 기물 하나를 넘고 보드판 내부를 자유롭게 이동할 수 있다")
    void move(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Piece soldier1 = Soldier.of(position.add(new Vector(1, 0)), Team.HAN);
        final Piece soldier2 = Soldier.of(position.add(new Vector(0, 1)), Team.HAN);
        final Piece soldier3 = Soldier.of(position.add(new Vector(-1, 0)), Team.HAN);
        final Piece soldier4 = Soldier.of(position.add(new Vector(0, -1)), Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(cannon, soldier1, soldier2, soldier3, soldier4)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        final Piece move = cannon.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("포는 규칙에 어긋나게 움직일 수 없다")
    void cannotMoveToInvalidDirection(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(cannon)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

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
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Board board = Board.from(Pieces.empty().addAll(List.of(cannon)));

        final Position movedPosition = position.add(new Vector(3, 0));

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,0", "4,0", "5,0", "0,2", "0,3", "0,4"})
    @DisplayName("포의 경로에 포가 포함되면 이동할 수 없다")
    void cannotMoveWhenExistCannonInRoute(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Piece otherAllyCannon1 = Cannon.of(position.add(new Vector(1, 0)), Team.HAN);
        final Piece otherAllyCannon2 = Cannon.of(position.add(new Vector(-1, 0)), Team.HAN);
        final Piece otherEnemyCannon1 = Cannon.of(position.add(new Vector(0, 1)), Team.CHO);
        final Piece otherEnemyCannon2 = Cannon.of(position.add(new Vector(0, -1)), Team.CHO);
        final Board board = Board.from(
                Pieces.empty().addAll(List.of(cannon, otherAllyCannon1, otherAllyCannon2, otherEnemyCannon1, otherEnemyCannon2)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 경로에 포가 존재할 때, 이동할 수 없습니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"3,0", "-3,0", "0,3", "0,-3"})
    @DisplayName("포의 경로에 1개 초과의 기물이 존재하면 이동할 수 없다")
    void cannotMoveWhenExistOtherPieceMoreThanOneInRoute(final int rowDirection, final int columnDirection) {
        // given
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Piece soldier1 = Soldier.of(position.add(new Vector(1, 0)), Team.CHO);
        final Piece soldier2 = Soldier.of(position.add(new Vector(2, 0)), Team.CHO);
        final Piece soldier3 = Soldier.of(position.add(new Vector(-1, 0)), Team.CHO);
        final Piece soldier4 = Soldier.of(position.add(new Vector(-2, 0)), Team.CHO);
        final Piece soldier5 = Soldier.of(position.add(new Vector(0, 1)), Team.CHO);
        final Piece soldier6 = Soldier.of(position.add(new Vector(0, 2)), Team.CHO);
        final Piece soldier7 = Soldier.of(position.add(new Vector(0, -1)), Team.CHO);
        final Piece soldier8 = Soldier.of(position.add(new Vector(0, -2)), Team.CHO);
        final Board board = Board.from(
                Pieces.empty().addAll(List.of(cannon, soldier1, soldier2, soldier3, soldier4, soldier5, soldier6, soldier7, soldier8)));

        final Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }

    @Test
    @DisplayName("포의 도착지에 적의 포가 포함되면 이동할 수 없다")
    void cannotMoveWhenExistCannonInDestination() {
        // given
        final Position position = Position.of(5, 5);
        final Piece cannon = Cannon.of(position, Team.HAN);
        final Piece soldier = Soldier.of(position.add(new Vector(1, 0)), Team.CHO);
        final Piece otherEnemyCannon = Cannon.of(position.add(new Vector(2, 0)), Team.CHO);
        final Board board = Board.from(
                Pieces.empty().addAll(List.of(cannon, soldier, otherEnemyCannon)));

        final Position movedPosition = position.add(new Vector(2, 0));

        // when
        // then
        assertThatThrownBy(() -> cannon.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 경로에 포가 존재할 때, 이동할 수 없습니다");
    }
}

package janggi.piece;

import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SoldierTest {

    @Test
    @DisplayName("5개의 졸/병은 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        List<Soldier> ChoSoldiers = Soldier.defaultsOf(Team.CHO);
        List<Soldier> HanSoldiers = Soldier.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoSoldiers.get(0).position).isEqualTo(Position.of(Team.decideRow(4, Team.CHO), 1));
            assertThat(ChoSoldiers.get(1).position).isEqualTo(Position.of(Team.decideRow(4, Team.CHO), 3));
            assertThat(ChoSoldiers.get(2).position).isEqualTo(Position.of(Team.decideRow(4, Team.CHO), 5));
            assertThat(ChoSoldiers.get(3).position).isEqualTo(Position.of(Team.decideRow(4, Team.CHO), 7));
            assertThat(ChoSoldiers.get(4).position).isEqualTo(Position.of(Team.decideRow(4, Team.CHO), 9));
            assertThat(HanSoldiers.get(0).position).isEqualTo(Position.of(Team.decideRow(4, Team.HAN), 1));
            assertThat(HanSoldiers.get(1).position).isEqualTo(Position.of(Team.decideRow(4, Team.HAN), 3));
            assertThat(HanSoldiers.get(2).position).isEqualTo(Position.of(Team.decideRow(4, Team.HAN), 5));
            assertThat(HanSoldiers.get(3).position).isEqualTo(Position.of(Team.decideRow(4, Team.HAN), 7));
            assertThat(HanSoldiers.get(4).position).isEqualTo(Position.of(Team.decideRow(4, Team.HAN), 9));
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 0, HAN", "0, 1, HAN", "0,-1, HAN", "0, -1, CHO", "0, 1, CHO", "-1, 0, CHO"})
    @DisplayName("졸/병은 적진을 향한 수직 혹은 수평으로 1칸 이동할 수 있다")
    void move(int rowDirection, int columnDirection, Team team) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = Soldier.of(position, team);
        Pieces pieces = new Pieces(List.of());
        Board board = Board.from(pieces.add(soldier));

        Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        Piece move = soldier.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"HAN, -1", "CHO, 1"})
    @DisplayName("졸/병은 본진 방향으로 이동할 수 없다")
    void cannotMoveToAllyBase(Team team, int deltaRow) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = Soldier.of(position, team);
        Pieces pieces = new Pieces(List.of());
        Board board = Board.from(pieces.add(soldier));

        Position movedPosition = position.add(new Vector(deltaRow, 0));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("본진을 향할 수 없습니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("졸/병은 2칸 이상 움직일 수 없다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = Soldier.of(position, Team.HAN);
        Pieces pieces = new Pieces(List.of());
        Board board = Board.from(pieces.add(soldier));

        Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

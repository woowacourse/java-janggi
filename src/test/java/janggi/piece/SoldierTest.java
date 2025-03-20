package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 0, RED", "0, 1, RED", "0,-1, RED", "0, -1, GREEN", "0, 1, GREEN", "-1, 0, GREEN"})
    @DisplayName("졸/병은 적진을 향한 수직 혹은 수평으로 1칸 이동할 수 있다")
    void move(int rowDirection, int columnDirection, Team team) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = new Soldier(position, team);
        Board board = Board.initialize(List.of(soldier));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        Piece move = soldier.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"RED, -1", "GREEN, 1"})
    @DisplayName("졸/병은 본진 방향으로 이동할 수 없다")
    void cannotMoveToAllyBase(Team team, int rowDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece soldier = new Soldier(position, team);
        Board board = Board.initialize(List.of(soldier));

        Position movedPosition = position.adjust(rowDirection, 0);

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
        Piece soldier = new Soldier(position, Team.RED);
        Board board = Board.initialize(List.of(soldier));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> soldier.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

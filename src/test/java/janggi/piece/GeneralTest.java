package janggi.piece;

import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralTest {

    @Test
    @DisplayName("궁은 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        General choGeneral = General.defaultOf(Team.CHO);
        General hanGeneral = General.defaultOf(Team.HAN);

        // then
        assertThat(choGeneral.position).isEqualTo(Position.of(Team.decideRow(2, Team.CHO), 5));
        assertThat(hanGeneral.position).isEqualTo(Position.of(Team.decideRow(2, Team.HAN), 5));
    }

    @Test
    @DisplayName("궁은 수직/수평으로 1칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Piece general = General.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().add(general));

        Position movedPosition = position.add(new Vector(-1, 0));

        // when
        Piece move = general.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("궁은 2칸 이상 움직일 수 없다")
    void move(int deltaRow, int deltaColumn) {
        // given
        Position position = Position.of(5, 5);
        Piece general = General.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().add(general));

        Position movedPosition = position.add(new Vector(deltaRow, deltaColumn));

        // when
        // then
        assertThatThrownBy(() -> general.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

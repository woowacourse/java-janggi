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

class GuardTest {

    @Test
    @DisplayName("2개의 사가 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        List<Guard> ChoGuards = Guard.defaultsOf(Team.CHO);
        List<Guard> HanGuards = Guard.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoGuards.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 4));
            assertThat(ChoGuards.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 6));
            assertThat(HanGuards.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 4));
            assertThat(HanGuards.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 6));
        });
    }

    @Test
    @DisplayName("사는 수직/수평으로 1칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Piece guard = Guard.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().add(guard));

        Position movedPosition = position.add(new Vector(-1, 0));

        // when
        Piece move = guard.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("사는 2칸 이상 움직일 수 없다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece guard = Guard.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().add(guard));

        Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> guard.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

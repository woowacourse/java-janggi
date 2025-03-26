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

class HorseTest {

    @Test
    @DisplayName("2개의 마가 올바른 위치에 초기화 될 수 있다")
    void defaultsOf() {
        // given
        // when
        List<Horse> ChoHorses = Horse.defaultsOf(Team.CHO);
        List<Horse> HanHorses = Horse.defaultsOf(Team.HAN);

        // then
        assertAll(() -> {
            assertThat(ChoHorses.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 3));
            assertThat(ChoHorses.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.CHO), 8));
            assertThat(HanHorses.get(0).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 3));
            assertThat(HanHorses.get(1).position).isEqualTo(Position.of(Team.decideRow(1, Team.HAN), 8));
        });
    }

    @Test
    @DisplayName("마는 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 1칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Piece horse = Horse.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().addAll(List.of(horse)));

        Position movedPosition = position.add(new Vector(1, 2));

        // when
        Piece move = horse.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 3", "3, 1", "2,2"})
    @DisplayName("마는 규칙에 어긋나게 움직일 수 없다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece horse = Horse.of(position, Team.HAN);
        Board board = Board.from(Pieces.empty().addAll(List.of(horse)));

        Position movedPosition = position.add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> horse.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

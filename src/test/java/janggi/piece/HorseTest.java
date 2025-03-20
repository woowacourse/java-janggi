package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HorseTest {

    @Test
    @DisplayName("마는 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 1칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Horse horse = new Horse(position, Team.RED);
        Board board = Board.initialize(List.of(horse));

        Position movedPosition = position.adjust(1, 2);

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
        Piece horse = new Horse(position, Team.RED);
        Board board = Board.initialize(List.of(horse));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> horse.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

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

class ElephantTest {

    @Test
    @DisplayName("상은 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 2칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Elephant elephant = new Elephant(position, Team.RED);
        Board board = Board.initialize(List.of(elephant));

        Position movedPosition = position.adjust(2, 3);

        // when
        Piece move = elephant.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 4", "4, 2", "3,3", "1,1"})
    @DisplayName("상은 규칙에 어긋나게 움직일 수 없다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece elephant = new Elephant(position, Team.RED);
        Board board = Board.initialize(List.of(elephant));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> elephant.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

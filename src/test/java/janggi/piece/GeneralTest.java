package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralTest {

    @Test
    @DisplayName("궁은 수직/수평으로 1칸 이동할 수 있다")
    void move() {
        // given
        Position position = Position.of(5, 5);
        Piece general = new General(position, Team.RED);
        Board board = Board.initialize(List.of(general));

        Position movedPosition = position.adjust(-1, 0);

        // when
        Piece move = general.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("궁은 2칸 이상 움직일 수 없다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece general = new General(position, Team.RED);
        Board board = Board.initialize(List.of(general));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> general.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

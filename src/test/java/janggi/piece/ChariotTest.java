package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChariotTest {

    @ParameterizedTest
    @CsvSource(value = {"1,0", "2,0", "3,0", "4,0", "5,0", "0,1", "0,2", "0,3", "0,4"})
    @DisplayName("차는 수직/수평으로 보드판 내부를 자유롭게 이동할 수 있다")
    void move(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece chariot = Chariot.of(position, Team.RED);
        Board board = Board.initialize(List.of(chariot));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        Piece move = chariot.move(board, movedPosition);

        // then
        assertThat(move.getPosition()).isEqualTo(movedPosition);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("차는 규칙에 어긋나게 움직일 수 없다")
    void cannotMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece chariot = Chariot.of(position, Team.RED);
        Board board = Board.initialize(List.of(chariot));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> chariot.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HorseTest {

    @Test
    @DisplayName("마는 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 1칸 이동할 수 있다")
    void checkCanMove() {
        // given
        Position position = Position.of(5, 5);
        Piece horse = new Horse(Team.RED);
        Board board = new Board(Map.of(position, horse));

        Position movedPosition = position.adjust(1, 2);

        // when
        // then
        assertDoesNotThrow(() -> horse.checkCanMove(board, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 3", "3, 1", "2,2"})
    @DisplayName("마는 규칙에 어긋나게 움직일 수 없다")
    void cannotCheckCanMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece horse = new Horse(Team.RED);
        Board board = new Board(Map.of(position, horse));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> horse.checkCanMove(board, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

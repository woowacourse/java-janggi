package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GuardTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 0", "0, 1", "0,-1", "0, -1"})
    @DisplayName("사는 수직/수평으로 1칸 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> guard.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 0"})
    @DisplayName("사는 2칸 이상 움직일 수 없다")
    void cannotCheckCanMoveToInvalidCount(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> guard.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

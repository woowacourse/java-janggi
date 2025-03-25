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

class ElephantTest {


    @ParameterizedTest
    @CsvSource(value = {"2,3", "-2,3", "2,-3", "-2,-3", "3,2", "-3,2", "3,-2", "-3,-2"})
    @DisplayName("상은 수직/수평으로 1칸 이동 후, 진행 방향의 대각선으로 2칸 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece elephant = new Elephant(Team.RED);
        Board board = new Board(Map.of(position, elephant));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> elephant.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 4", "4, 2", "3,3", "1,1"})
    @DisplayName("상은 규칙에 어긋나게 움직일 수 없다")
    void cannotCheckCanMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece elephant = new Elephant(Team.RED);
        Board board = new Board(Map.of(position, elephant));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> elephant.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

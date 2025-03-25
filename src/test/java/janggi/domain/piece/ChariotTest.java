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

class ChariotTest {

    @ParameterizedTest
    @CsvSource(value = {"1,0", "2,0", "3,0", "4,0", "5,0", "0,1", "0,2", "0,3", "0,4"})
    @DisplayName("차는 수직/수평으로 보드판 내부를 자유롭게 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece chariot = new Chariot(Team.RED);
        Board board = new Board(Map.of(position, chariot));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> chariot.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "2, 2", "-1,-2", "-2,-3"})
    @DisplayName("차는 규칙에 어긋나게 움직일 수 없다")
    void cannotCheckCanMoveToInvalidDirection(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece chariot = new Chariot(Team.RED);
        Board board = new Board(Map.of(position, chariot));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> chariot.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동할 수 없는 지점입니다.");
    }
}

package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GuardTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 0", "0, 1", "0, -1", "-1, 0"})
    @DisplayName("사는 수직/수평으로 1칸 이동할 수 있다")
    void checkCanMove(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(2, 5);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> guard.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "-1, 1", "1,-1", "-1, -1"})
    @DisplayName("사는 궁성 중심에서 출발할 때에는 대각선으로 1칸 이동할 수 있다")
    void checkCanMoveFromPalaceCenter(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(2, 5);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> guard.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 4", "1, 6", "3,4", "3, 6"})
    @DisplayName("사는 궁성 중심으로 이동 할 때에는 대각선으로 1칸 이동할 수 있다")
    void checkCanMoveToPalaceCenter(int positionRow, int positionColumn) {
        // given
        Position position = Position.of(positionRow, positionColumn);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = Position.of(2, 5);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertDoesNotThrow(() -> guard.checkCanMove(placement, position, movedPosition));
    }

    @ParameterizedTest
    @CsvSource(value = {"2, 0", "0, 2"})
    @DisplayName("사는 2칸 이상 움직일 수 없다")
    void checkCanNotMoveToInvalidCount(int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(1, 4);
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(position, guard));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> guard.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수직/수평으로 1칸만 이동 가능합니다.");
    }

    private static Stream<Arguments> provideOutOfPalacePosition() {
        return Stream.of(Arguments.of(Position.of(2, 4), Position.of(2, 3)),
                Arguments.of(Position.of(3, 5), Position.of(4, 5)),
                Arguments.of(Position.of(2, 6), Position.of(2, 7)));
    }

    @ParameterizedTest
    @MethodSource("provideOutOfPalacePosition")
    @DisplayName("사는 궁성 밖으로 나갈 수 없다")
    void checkCanNotMoveToOutOfPalace(Position departure, Position destination) {
        // given
        Piece guard = new Guard(Team.RED);
        Board board = new Board(Map.of(departure, guard));

        Placement placement = new Placement(board, departure, destination);

        // when
        // then
        assertThatThrownBy(() -> guard.checkCanMove(placement, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사는 궁성 내부로만 이동할 수 있습니다.");
    }
}

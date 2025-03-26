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

    private static Stream<Arguments> providePositionOfDiagonal() {
        return Stream.of(Arguments.of(Position.of(1, 4), Position.of(2, 5)),
                Arguments.of(Position.of(1, 4), Position.of(3, 6)),
                Arguments.of(Position.of(1, 6), Position.of(2, 5)),
                Arguments.of(Position.of(1, 6), Position.of(3, 4)),
                Arguments.of(Position.of(3, 4), Position.of(2, 5)),
                Arguments.of(Position.of(3, 4), Position.of(1, 6)),
                Arguments.of(Position.of(3, 6), Position.of(2, 5)),
                Arguments.of(Position.of(3, 6), Position.of(1, 4)));
    }

    @ParameterizedTest
    @MethodSource("providePositionOfDiagonal")
    @DisplayName("차가 궁성 내부에 위치하고, 경로에 궁성의 중심만 존재하면 대각선으로 이동할 수 있다")
    void checkCanMove(Position departure, Position destination) {
        // given
        Piece chariot = new Chariot(Team.RED);
        Board board = new Board(Map.of(departure, chariot));

        Placement placement = new Placement(board, departure, destination);

        // when
        // then
        assertDoesNotThrow(() -> chariot.checkCanMove(placement, departure, destination));
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
                .hasMessageContaining("수직/수평 방향 직선으로만 이동 가능합니다.");
    }
}

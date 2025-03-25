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
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PieceTest {

    private static Stream<Arguments> providePiece() {
        Position position = Position.of(5, 5);
        Team team = Team.RED;
        return Stream.of(
                Arguments.of(position, new Horse(team), 1, 2),
                Arguments.of(position, new Elephant(team), 2, 3),
                Arguments.of(position, new Chariot(team), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("providePiece")
    @DisplayName("포가 아닌 1칸 초과 이동하는 기물은 경로에 기물이 존재할 경우 이동할 수 없다")
    void cannotCheckCanMoveWhenExistPieceInRoute(Position position, Piece piece, int rowDirection,
                                                 int columnDirection) {
        // given
        Piece otherPiece1 = new Soldier(Team.RED);
        Piece otherPiece2 = new Soldier(Team.RED);
        Piece otherPiece3 = new Soldier(Team.RED);
        Piece otherPiece4 = new Soldier(Team.RED);
        Board board = new Board(Map.of(position, piece,
                position.adjust(-1, 0), otherPiece1,
                position.adjust(0, 1), otherPiece2,
                position.adjust(0, -1), otherPiece3,
                position.adjust(1, 0), otherPiece4));

        Position movedPosition = position.adjust(rowDirection, columnDirection);
        Placement placement = new Placement(board, position, movedPosition);

        // when
        // then
        assertThatThrownBy(() -> piece.checkCanMove(placement, position, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물이 존재합니다.");
    }
}

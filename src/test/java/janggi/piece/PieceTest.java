package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import java.util.List;
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
                Arguments.of(new Horse(position, team), 1, 2),
                Arguments.of(new Elephant(position, team), 2, 3),
                Arguments.of(new Chariot(position, team), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("providePiece")
    @DisplayName("포가 아닌 1칸 초과 이동하는 기물은 경로에 기물이 존재할 경우 이동할 수 없다")
    void cannotMoveWhenExistPieceInRoute(Piece piece, int rowDirection, int columnDirection) {
        // given
        Position position = Position.of(5, 5);
        Piece otherPiece1 = new Soldier(position.adjust(1, 0), Team.RED);
        Piece otherPiece2 = new Soldier(position.adjust(-1, 0), Team.RED);
        Piece otherPiece3 = new Soldier(position.adjust(0, 1), Team.RED);
        Piece otherPiece4 = new Soldier(position.adjust(0, -1), Team.RED);
        Board board = Board.initialize(
                List.of(piece, otherPiece1, otherPiece2, otherPiece3, otherPiece4));

        Position movedPosition = position.adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> piece.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물이 존재합니다.");
    }

    private static Stream<Arguments> provideAllyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.RED;
        return Stream.of(
                Arguments.of(new Guard(position, team), new Soldier(position.adjust(1, 0), team), 1, 0),
                Arguments.of(new General(position, team), new Soldier(position.adjust(1, 0), team), 1, 0),
                Arguments.of(new Soldier(position, team), new Soldier(position.adjust(1, 0), team), 1, 0),
                Arguments.of(new Horse(position, team), new Soldier(position.adjust(1, 2), team), 1, 2),
                Arguments.of(new Elephant(position, team), new Soldier(position.adjust(2, 3), team), 2, 3),
                Arguments.of(new Chariot(position, team), new Soldier(position.adjust(3, 0), team), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("provideAllyPiece")
    @DisplayName("포가 아닌 기물은 목적지에 아군이 존재할 경우 이동할 수 없다")
    void cannotMoveWhenExistAllyPieceInDestination(Piece piece, Piece allyPiece, int rowDirection,
                                                   int columnDirection) {
        // given
        Board board = Board.initialize(List.of(piece, allyPiece));

        Position movedPosition = piece.getPosition().adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> piece.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("목적지에 아군이 존재합니다.");
    }
}

package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import board.Board;
import board.Position;

class SoldierTest {

    private final Position initPosition = new Position(4, 3);

    @Test
    void 한나라_졸의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new Soldier(Team.RED);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Soldier(Team.BLUE),
                new Position(5, 3), new Soldier(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2)
        );
    }

    @Test
    void 초나라_졸의_이동할_수_있는_위치를_계산한다() {
        Piece piece = new Soldier(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Soldier(Team.RED),
                new Position(5, 3), new Soldier(Team.RED)

        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(4, 2),
                new Position(3, 3)
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_초나라_졸은_아래_방향으로_움직일_수_없다(int row, int column, Set<Position> expected) {
        Piece piece = new Soldier(Team.BLUE);
        Position soldierPosition = new Position(row, column);
        Board board = new Board(Map.of(
                soldierPosition, piece
        ));

        assertThat(piece.getMovablePositions(soldierPosition, board))
                .doesNotContainAnyElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_초나라_졸은_아래_방향으로_움직일_수_없다() {
        return Stream.of(
                Arguments.of(1, 4, Set.of(
                        new Position(2, 4), new Position(2, 5)
                )),
                Arguments.of(1, 6, Set.of(
                        new Position(2, 6), new Position(2, 5)
                )),
                Arguments.of(2, 5, Set.of(
                        new Position(3, 4), new Position(3, 5), new Position(3, 6)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 궁성_영역의_한나라_졸은_위_방향으로_움직일_수_없다(int row, int column, Set<Position> expected) {
        Piece piece = new Soldier(Team.RED);
        Position soldierPosition = new Position(row, column);
        Board board = new Board(Map.of(
                soldierPosition, piece
        ));

        assertThat(piece.getMovablePositions(soldierPosition, board))
                .doesNotContainAnyElementsOf(expected);
    }

    private static Stream<Arguments> 궁성_영역의_한나라_졸은_위_방향으로_움직일_수_없다() {
        return Stream.of(
                Arguments.of(10, 4, Set.of(
                        new Position(9, 4), new Position(9, 5)
                )),
                Arguments.of(10, 6, Set.of(
                        new Position(9, 6), new Position(9, 5)
                )),
                Arguments.of(9, 5, Set.of(
                        new Position(8, 4), new Position(8, 5), new Position(8, 6)
                ))
        );
    }

}

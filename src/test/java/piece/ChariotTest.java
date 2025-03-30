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

class ChariotTest {

    private final Position initPosition = new Position(5, 4);

    @Test
    void 차는_동서남북_방향으로_계속_움직인다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(initPosition, piece));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                // 북
                new Position(1, 4),
                new Position(2, 4),
                new Position(3, 4),
                new Position(4, 4),

                // 남
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4),
                new Position(9, 4),
                new Position(10, 4),

                // 서
                new Position(5, 1),
                new Position(5, 2),
                new Position(5, 3),

                // 동
                new Position(5, 5),
                new Position(5, 6),
                new Position(5, 7),
                new Position(5, 8),
                new Position(5, 9)
        );
    }

    @Test
    void 차는_같은_팀_기물이_존재하면_멈춘다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 4), new Chariot(Team.BLUE),
                new Position(6, 4), new Chariot(Team.BLUE),
                new Position(5, 3), new Chariot(Team.BLUE),
                new Position(5, 5), new Chariot(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(2, 4),
                new Position(3, 4),
                new Position(4, 4)
        );
    }

    @Test
    void 차는_다른_팀_기물이_존재하면_취한_후_멈춘다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Chariot(Team.RED),
                new Position(6, 4), new Chariot(Team.RED),
                new Position(5, 3), new Chariot(Team.RED),
                new Position(5, 5), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(6, 4),
                new Position(5, 3),
                new Position(5, 5)
        );
    }

    @MethodSource
    @ParameterizedTest
    void 차의_궁성_영역_대각선_움직임을_구한다(int row, int column, Set<Position> expected) {
        Piece piece = new Chariot(Team.BLUE);
        Piece alliesPiece = new Chariot(Team.BLUE);
        Position chariotPosition = new Position(row, column);
        Board board = new Board(Map.of(
                chariotPosition, piece,
                new Position(row + 1, column), alliesPiece,
                new Position(row - 1, column), alliesPiece,
                new Position(row, column + 1), alliesPiece,
                new Position(row, column - 1), alliesPiece
        ));

        assertThat(piece.getMovablePositions(chariotPosition, board))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 차의_궁성_영역_대각선_움직임을_구한다() {
        return Stream.of(
                Arguments.of(1, 4, Set.of(new Position(2, 5), new Position(3, 6))),
                Arguments.of(1, 6, Set.of(new Position(2, 5), new Position(3, 4))),
                Arguments.of(3, 4, Set.of(new Position(2, 5), new Position(1, 6))),
                Arguments.of(3, 6, Set.of(new Position(2, 5), new Position(1, 4))),
                Arguments.of(8, 4, Set.of(new Position(9, 5), new Position(10, 6))),
                Arguments.of(8, 6, Set.of(new Position(9, 5), new Position(10, 4))),
                Arguments.of(10, 4, Set.of(new Position(9, 5), new Position(8, 6))),
                Arguments.of(10, 6, Set.of(new Position(9, 5), new Position(8, 4))),
                Arguments.of(2, 5,
                        Set.of(new Position(1, 4), new Position(1, 6), new Position(3, 4), new Position(3, 6))
                ),
                Arguments.of(9, 5,
                        Set.of(new Position(8, 4), new Position(8, 6), new Position(10, 4), new Position(10, 6))
                )
        );
    }

}

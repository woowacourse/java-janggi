package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PathBasedMoveStrategyTest {

    @Test
    @DisplayName("경로에 따라 정확한 목적지를 생성한다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);

        List<List<Direction>> paths = List.of(
                List.of(Direction.UP, Direction.UP), // (4,4) -> (3,4) -> (2,4)
                List.of(Direction.LEFT, Direction.LEFT) // (4,4) -> (4,3) -> (4,2)
        );

        PathBasedMoveStrategy strategy = new PathBasedMoveStrategy(paths);

        Board board = mock(Board.class);
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).contains(
                new Position(2, 4),
                new Position(4, 2)
        );
    }

    @Test
    @DisplayName("경로에 따라 정확한 목적지를 생성한다.")
    void moveStrategy_Diagonal_Test() {
        // given
        Position start = new Position(4, 4);

        List<List<Direction>> paths = List.of(
                List.of(Direction.UP, Direction.UP_LEFT), // (4,4) -> (3,4) -> (2,3)
                List.of(Direction.RIGHT, Direction.DOWN_RIGHT) // (4,4) -> (4,5) -> (5,6)
        );

        PathBasedMoveStrategy strategy = new PathBasedMoveStrategy(paths);

        Board board = mock(Board.class);
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).contains(
                new Position(2, 3),
                new Position(5, 6)
        );
    }
}

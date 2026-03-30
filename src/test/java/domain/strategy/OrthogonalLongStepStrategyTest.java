package domain.strategy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

class OrthogonalLongStepStrategyTest {

    static class BlockTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(0, 5), new Pawn(Side.HAN));
            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("직선으로 끝까지 이동한다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);
        OrthogonalLongStepStrategy strategy = new OrthogonalLongStepStrategy();

        Board board = new Board(new BlockTest().initialize());
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).containsOnly(
                new Position(0, 4),
                new Position(1, 4),
                new Position(2, 4),
                new Position(3, 4),
                new Position(5, 4),
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4),
                new Position(9, 4),

                new Position(4, 0),
                new Position(4, 1),
                new Position(4, 2),
                new Position(4, 3),
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7),
                new Position(4, 8)
        );
    }

    @Test
    @DisplayName("기물을 만나면 거기까지만 이동한다.")
    void moveStrategy_Block_Test() {
        // given
        Position start = new Position(0, 2);
        OrthogonalLongStepStrategy strategy = new OrthogonalLongStepStrategy();

        Board board = new Board(new BlockTest().initialize());
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).contains(new Position(0, 5));
        assertThat(result).doesNotContain(
                new Position(0, 6),
                new Position(0, 7),
                new Position(0, 8)
        );
    }
}

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

class OrthogonalStepStrategyTest {

    static class BlockTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(0, 1), new Pawn(Side.HAN));
            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("상하좌우 한칸 이동 위치를 생성한다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);
        OrthogonalStepStrategy strategy = new OrthogonalStepStrategy();

        Board board = mock(Board.class);
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).contains(
                new Position(3, 4),
                new Position(5, 4),
                new Position(4, 3),
                new Position(4, 5)
        );
    }

    @Test
    @DisplayName("보드 상관없이 이동 규칙에 맞게 상하좌우 한칸 이동 위치를 생성한다.")
    void moveStrategy_block_Test() {
        // given
        Position start = new Position(0, 0);
        OrthogonalStepStrategy strategy = new OrthogonalStepStrategy();

        Board board = new Board(new BlockTest().initialize());
        Piece piece = mock(Piece.class);

        // when
        List<Position> result = strategy.generate(board, start, piece);

        // then
        assertThat(result).contains(
                new Position(0, 1),
                new Position(1, 0)
        );
    }
}

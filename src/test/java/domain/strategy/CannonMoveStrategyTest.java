package domain.strategy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CannonMoveStrategyTest {

    static class CannonMoveTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();
            piecesPosition.put(new Position(3, 4), new Pawn(Side.HAN));

            piecesPosition.put(new Position(4, 6), new Pawn(Side.CHU));
            piecesPosition.put(new Position(4, 7), new Pawn(Side.CHU));

            piecesPosition.put(new Position(4, 2), new Cannon(Side.CHU));
            piecesPosition.put(new Position(4, 1), new Cannon(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("다른 기물이 없으면 이동할 수 없다.")
    void moveStrategy_DoesNotJump_Test() {
        // given
        Board board = new Board(Map.of());
        Position start = new Position(4, 4);
        Piece cannon = new Cannon(Side.HAN);

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.generate(board, start, cannon);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("다른 기물 하나를 점프하면 이동할 수 없다.")
    void moveStrategy_Jump_test() {
        // given
        Board board = new Board(new CannonMoveTest().initialize());
        Position start = new Position(4, 4);
        Piece cannon = new Cannon(Side.HAN);

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.generate(board, start, cannon);

        // then
        assertThat(result).contains(
                new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4)
        );
    }

    @Test
    @DisplayName("상대말을 만나면 잡고 멈춘다.")
    void moveStrategy_Capture_test() {
        // given
        Board board = new Board(new CannonMoveTest().initialize());
        Position start = new Position(4, 4);
        Piece cannon = new Cannon(Side.HAN);

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.generate(board, start, cannon);

        // then
        assertThat(result).contains(new Position(4, 7));
        assertThat(result).doesNotContain(new Position(4, 8));
    }

    @Test
    @DisplayName("포는 포를 넘거나 잡을 수 없다.")
    void moveStrategy_CannonRule_test() {
        // given
        Board board = new Board(new CannonMoveTest().initialize());
        Position start = new Position(2, 2);
        Piece cannon = new Cannon(Side.HAN);

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.generate(board, start, cannon);

        // then
        assertThat(result).isEmpty();
    }
}

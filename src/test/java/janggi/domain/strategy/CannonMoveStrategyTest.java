package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Destinations;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {

    @DisplayName("포는 이동 경로에 반드시 다른 기물이 하나 있어야 넘어서 이동할 수 있다")
    @Test
    void moveablePositions() {
        CannonMoveStrategy strategy = new CannonMoveStrategy();
        Position current = new Position(5, 5);
        Piece cannon = new Piece(Side.CHO, PieceType.CANNON, "0");

        EnumSet<Direction> dirs = EnumSet.of(Direction.N);
        Destinations paths = strategy.findMovablePaths(current, dirs, );

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(4, 5), new Piece(Side.HAN, PieceType.SOLDIER, "0"));

        List<Position> destinations = strategy.moveablePositions(, paths, , boardState);

        assertThat(destinations).containsExactly(
                new Position(3, 5), new Position(2, 5), new Position(1, 5), new Position(0, 5)
        );
    }

    @DisplayName("포는 다른 포를 포다리로 삼거나 포를 잡을 수 없다")
    @Test
    void moveablePositions_BridgeOrTargetIsCannon_ReturnsEmpty() {
        CannonMoveStrategy strategy = new CannonMoveStrategy();
        Position current = new Position(5, 5);
        Piece cannon = new Piece(Side.CHO, PieceType.CANNON, "0");

        EnumSet<Direction> dirs = EnumSet.of(Direction.N);
        Destinations paths = strategy.findMovablePaths(current, dirs, );

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(4, 5), new Piece(Side.HAN, PieceType.CANNON, "1"));

        List<Position> destinations = strategy.moveablePositions(, paths, , boardState);

        assertThat(destinations).isEmpty();
    }
}

package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StepMoveStrategyTest {

    @DisplayName("지정된 방향으로 1칸 이동 시 아군 기물이 없으면 목적지로 산출된다")
    @Test
    void determineDestinations_NoObstacles_ReturnsDestination() {
        StepMoveStrategy strategy = new StepMoveStrategy();
        Position current = new Position(5, 5);
        Piece soldier = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        Paths paths = strategy.findMovablePaths(current, EnumSet.of(Direction.N));
        Map<Position, Piece> boardState = new HashMap<>();

        List<Position> destinations = strategy.determineDestinations(paths, boardState, soldier);

        assertThat(destinations).containsExactly(new Position(4, 5));
    }

    @DisplayName("이동하려는 1칸 위치에 아군 기물이 있으면 이동할 수 없다")
    @Test
    void determineDestinations_BlockedBySameSide_ReturnsEmpty() {
        StepMoveStrategy strategy = new StepMoveStrategy();
        Position current = new Position(5, 5);
        Piece soldier = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        Paths paths = strategy.findMovablePaths(current, EnumSet.of(Direction.N));
        Map<Position, Piece> boardState = new HashMap<>();

        boardState.put(new Position(4, 5), new Piece(Side.CHO, PieceType.HORSE, "1"));

        List<Position> destinations = strategy.determineDestinations(paths, boardState, soldier);

        assertThat(destinations).isEmpty();
    }
}

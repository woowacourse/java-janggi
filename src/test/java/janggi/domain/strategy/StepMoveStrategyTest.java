package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StepMoveStrategyTest {

    @DisplayName("지정된 방향으로 1칸 이동 시 아군 기물이 없으면 목적지로 산출된다")
    @Test
    void findDestinations_NoObstacles_ReturnsDestination() {
        StepMoveStrategy strategy = new StepMoveStrategy();
        Position current = new Position(5, 5);
        Piece soldier = new Piece(Side.CHO, PieceType.SOLDIER, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, soldier);
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.getDestinations()).containsExactly(new Position(4, 5));
    }

    @DisplayName("이동하려는 1칸 위치에 아군 기물이 있으면 이동할 수 없다")
    @Test
    void findDestinations_BlockedBySameSide_ReturnsEmpty() {
        StepMoveStrategy strategy = new StepMoveStrategy();
        Position current = new Position(5, 5);
        Piece soldier = new Piece(Side.CHO, PieceType.SOLDIER, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, soldier);
        boardState.put(new Position(4, 5), new Piece(Side.CHO, PieceType.HORSE, "1"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.isEmpty()).isTrue();
    }
}

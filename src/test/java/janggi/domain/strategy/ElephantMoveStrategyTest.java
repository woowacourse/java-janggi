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

class ElephantMoveStrategyTest {

    @DisplayName("상은 직진 1칸, 대각선 2칸 이동 경로에 장애물이 없으면 목적지로 산출된다")
    @Test
    void findDestinations_NoObstacles_ReturnsDestination() {
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position current = new Position(5, 5);
        Piece elephant = new Piece(Side.CHO, PieceType.ELEPHANT, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, elephant);
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.getDestinations()).containsExactlyInAnyOrder(
                new Position(2, 7),
                new Position(2, 3)
        );
    }

    @DisplayName("상의 이동 경로 중간에 기물이 있으면 해당 방향으로는 이동할 수 없다")
    @Test
    void findDestinations_ObstacleInPath_BlocksMovement() {
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position current = new Position(5, 5);
        Piece elephant = new Piece(Side.CHO, PieceType.ELEPHANT, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, elephant);
        boardState.put(new Position(4, 5), new Piece(Side.HAN, PieceType.SOLDIER, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.isEmpty()).isTrue();
    }
}

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

class SlideMoveStrategyTest {

    @DisplayName("차는 이동 경로 상에 기물이 없으면 끝까지 이동 가능 목적지로 산출한다")
    @Test
    void findDestinations_NoObstacles_ReturnsAllPaths() {
        SlideMoveStrategy strategy = new SlideMoveStrategy();
        Position current = new Position(5, 5);
        Piece chariot = new Piece(Side.CHO, PieceType.CHARIOT, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, chariot);
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.getDestinations()).containsExactly(
                new Position(4, 5), new Position(3, 5), new Position(2, 5),
                new Position(1, 5), new Position(0, 5)
        );
    }

    @DisplayName("차는 이동 중 상대 기물을 만나면 그 위치까지만 이동할 수 있고 넘어갈 수 없다")
    @Test
    void findDestinations_EnemyObstacle_StopsAtEnemy() {
        SlideMoveStrategy strategy = new SlideMoveStrategy();
        Position current = new Position(5, 5);
        Piece chariot = new Piece(Side.CHO, PieceType.CHARIOT, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, chariot);
        boardState.put(new Position(3, 5), new Piece(Side.HAN, PieceType.SOLDIER, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(current, EnumSet.of(Direction.N), board);

        assertThat(destinations.getDestinations()).containsExactly(
                new Position(4, 5), new Position(3, 5)
        );
    }
}

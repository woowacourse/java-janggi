package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.strategy.ElephantMoveStrategy;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantMoveStrategyTest {
    private final ElephantMoveStrategy strategy = new ElephantMoveStrategy();

    @DisplayName("상의 이동 경로는 3개의 좌표(멱1, 멱2, 도착지)로 구성되어야 한다. - findMovablePaths()")
    @Test
    void 상_경로_테스트() {
        // given
        Position current = new Position(4, 4);
        EnumSet<Direction> directions = EnumSet.of(Direction.N);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Path> pathList = new ArrayList<>();
        paths.forEach(pathList::add);
        assertThat(pathList).hasSize(2);

        List<Position> pathPositions = MoveStrategyTestHelper.toList(pathList.getFirst());
        assertThat(pathPositions).hasSize(3);
    }

    @DisplayName("첫 번째 멱이나 두 번째 멱 중 하나라도 막히는 경우, 이동할 수 없다. - determineDestinations()")
    @Test
    void 멱_막힘_테스트() {
        // given
        Position transit1 = new Position(3, 4);
        Position transit2 = new Position(2, 5);
        Position dest = new Position(1, 6);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit1, transit2, dest));

        // 첫 번째 멱이 막힌 경우
        Map<Position, Piece> boardState1 = new HashMap<>();
        boardState1.put(transit1, new Piece(Side.HAN, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.ELEPHANT, "1");

        assertThat(strategy.determineDestinations(routes, boardState1, me)).isEmpty();

        // 두 번째 멱이 막힌 경우
        Map<Position, Piece> boardState2 = new HashMap<>();
        boardState2.put(transit2, new Piece(Side.HAN, PieceType.SOLDIER, "2"));

        assertThat(strategy.determineDestinations(routes, boardState2, me)).isEmpty();
    }

    @DisplayName("모든 멱이 비어있고, 도착지에 적군이 있는 경우, 포획 가능하다. - determineDestinations()")
    @Test
    void 정상_이동_및_포획_테스트() {
        // given
        Position transit1 = new Position(3, 4);
        Position transit2 = new Position(2, 5);
        Position dest = new Position(1, 6);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit1, transit2, dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.HAN, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.ELEPHANT, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).containsExactly(dest);
    }

    @DisplayName("멱이 비어도, 도착지에 아군이 있는 경우, 이동할 수 없다. - determineDestinations()")
    @Test
    void 아군_막힘_테스트() {
        // given
        Position transit1 = new Position(3, 4);
        Position transit2 = new Position(2, 5);
        Position dest = new Position(1, 6);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit1, transit2, dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.CHO, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.ELEPHANT, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }

}
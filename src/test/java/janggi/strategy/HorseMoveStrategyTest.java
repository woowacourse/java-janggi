package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.strategy.HorseMoveStrategy;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseMoveStrategyTest {
    private final HorseMoveStrategy strategy = new HorseMoveStrategy();

    @DisplayName("한 방향에 대해, 대각선으로 갈라지는 두 개의 경로를 생성한다. - findMovablePaths()")
    @Test
    void 마_경로_생성_테스트() {
        // given
        Position current = new Position(3, 3);
        EnumSet<Direction> directions = EnumSet.of(Direction.N);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        // 북쪽 기준으로 북동, 북서 두 갈래 경로
        List<Path> pathList = new ArrayList<>();
        paths.forEach(pathList::add);
        assertThat(pathList).hasSize(2);

        List<Position> firstPathPositions = MoveStrategyTestHelper.toList(pathList.getFirst());
        assertThat(firstPathPositions).hasSize(2);
        assertThat(firstPathPositions.get(0)).isEqualTo(new Position(2, 3)); // 멱 위치 확인
        assertThat(firstPathPositions.get(1)).isEqualTo(new Position(1, 4)); // 도착지 확인
    }

    @DisplayName("멱에 기물이 있으면 이동할 수 없다. - determineDestinations()")
    @Test
    void 멱이_막힌_경우_이동_불가_테스트() {
        // given
        Position transit = new Position(2, 3);
        Position dest = new Position(1, 4);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit, dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(transit, new Piece(Side.HAN, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.HORSE, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("멱이 비어있고, 도착지에 적군이 있으면 포획 가능하다. - determineDestinations()")
    @Test
    void 멱_비고_적군_존재_시_이동_가능_테스트() {
        // given
        Position transit = new Position(2, 3);
        Position dest = new Position(1, 4);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit, dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.HAN, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.HORSE, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).containsExactly(dest);
    }

    @DisplayName("멱이 비어있어도, 도착지에 아군이 있으면 이동할 수 없다. - determineDestinations()")
    @Test
    void 멱_비어도_아군_존재_시_이동_불가_테스트() {
        // given
        Position transit = new Position(2, 3);
        Position dest = new Position(1, 4);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(transit, dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.CHO, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.HORSE, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }
}
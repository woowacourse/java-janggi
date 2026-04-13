package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlideMoveStrategyTest {
    private final MoveStrategy strategy = new SlideMoveStrategy();

    @DisplayName("장애물이 없는 경우, 보드 끝까지의 경로를 생성한다. - findMovablePath()")
    @Test
    void 장애물_없는_경우_경로_생성_테스트() {
        // given
        Position current = new Position(0, 0);
        EnumSet<Direction> directions = EnumSet.of(Direction.S);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        Path southPath = paths.iterator().next(); // 첫 번째 경로 꺼내기
        List<Position> positions = MoveStrategyTestHelper.toList(southPath);

        assertThat(positions)
                .hasSize(9) // (0, 0) 제외, (1, 0) ~ (9, 0) 9칸
                .contains(new Position(1, 0), new Position(9, 0));
    }

    @DisplayName("경로 중간에 적군이 있는 경우, 그 적군 위치까지만 이동 가능하다. - determineDestinations()")
    @Test
    void 경로에_적군_있는_경우_이동_결정_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(
                List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(2, 0), new Piece(Side.HAN, PieceType.SOLDIER, "1"));
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHARIOT, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, movingPiece);

        // then
        // 빈칸(1, 0) 가능, 적군(2, 0) 잡기 가능, 그 뒤(3, 0) 불가
        assertThat(destinations).containsExactly(new Position(1, 0), new Position(2, 0));
    }

    @DisplayName("경로 중간에 아군이 있는 경우, 그 직전까지만 이동 가능하다. - determineDestinations()")
    @Test
    void 경로에_아군_있는_경우_이동_결정_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(
                List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(2, 0), new Piece(Side.CHO, PieceType.SOLDIER, "1"));
        Piece movingPiece = new Piece(Side.CHO, PieceType.CHARIOT, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, movingPiece);

        // then
        // 빈칸(1, 0)만 가능, 아군(2, 0)부터 막힘
        assertThat(destinations).containsExactly(new Position(1, 0));
    }

    @DisplayName("궁성의 꼭짓점인 경우, [궁성 테두리, 궁성 중앙, 반대편 꼭짓점]을 포함하여 경로를 생성한다.")
    @Test
    void 궁성_꼭짓점_경로_생성_테스트() {
        // given
        Position current = new Position(0, 3);
        EnumSet<Direction> directions = EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Position> allPositions = new ArrayList<>();
        paths.forEach(path -> path.forEach(allPositions::add));
        assertThat(allPositions).containsExactlyInAnyOrder(
                // 동쪽 직진
                new Position(0, 4), new Position(0, 5), new Position(0, 6), new Position(0, 7), new Position(0, 8),

                // 서쪽 직진
                new Position(0, 2), new Position(0, 1), new Position(0, 0),

                // 남쪽 직진
                new Position(1, 3), new Position(2, 3), new Position(3, 3), new Position(4, 3), new Position(5, 3),
                new Position(6, 3), new Position(7, 3), new Position(8, 3), new Position(9, 3),

                // 남동쪽 대각선 (궁성 정중앙, 우하단 꼭짓점)
                new Position(1, 4), new Position(2, 5)
        );
    }

    @DisplayName("궁성의 중앙인 경우, [궁성 내 상하좌우, 대각선]을 포함하여 경로를 생성한다.")
    @Test
    void 궁성_정중앙_경로_생성_테스트() {
        // given
        Position current = new Position(1, 4);
        EnumSet<Direction> directions = EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Position> allReachablePositions = new ArrayList<>();
        paths.forEach(path -> path.forEach(allReachablePositions::add));

        assertThat(allReachablePositions).containsExactlyInAnyOrder(
                // 북쪽 직진
                new Position(0, 4),

                // 남쪽 직진
                new Position(2, 4), new Position(3, 4), new Position(4, 4), new Position(5, 4),
                new Position(6, 4), new Position(7, 4), new Position(8, 4), new Position(9, 4),

                // 동쪽 직진
                new Position(1, 5), new Position(1, 6), new Position(1, 7), new Position(1, 8),
                // 서쪽 직진

                new Position(1, 3), new Position(1, 2), new Position(1, 1), new Position(1, 0),

                // 대각선 4방향 (궁성 꼭짓점)
                new Position(0, 3),
                new Position(0, 5),
                new Position(2, 3),
                new Position(2, 5)
        );
    }

    @DisplayName("궁성의 변의 중앙인 경우, [변의 꼭짓점, 궁성 정중앙, 반대편 변의 중앙]을 포함하여 경로를 생성한다.")
    @Test
    void 궁성_변_중앙_경로_생성_테스트() {
        // given
        Position current = new Position(1, 3);
        EnumSet<Direction> directions = EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Position> allReachablePositions = new ArrayList<>();
        paths.forEach(path -> path.forEach(allReachablePositions::add));

        assertThat(allReachablePositions).containsExactlyInAnyOrder(
                // 북쪽 직진
                new Position(0, 3),

                // 남쪽 직진
                new Position(2, 3), new Position(3, 3), new Position(4, 3), new Position(5, 3),
                new Position(6, 3), new Position(7, 3), new Position(8, 3), new Position(9, 3),

                // 동쪽 직진
                new Position(1, 4), new Position(1, 5), new Position(1, 6), new Position(1, 7), new Position(1, 8),

                // 서쪽 직진
                new Position(1, 2), new Position(1, 1), new Position(1, 0)
        );
    }
}
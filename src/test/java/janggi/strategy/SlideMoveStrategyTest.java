package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.strategy.SlideMoveStrategy;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlideMoveStrategyTest {
    private final SlideMoveStrategy strategy = new SlideMoveStrategy();

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
}
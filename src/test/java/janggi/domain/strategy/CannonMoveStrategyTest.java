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

public class CannonMoveStrategyTest {
    private final MoveStrategy strategy = new CannonMoveStrategy();

    @DisplayName("장애물 여부와 상관없이 직선 경로를 생성한다. - findMovablePaths()")
    @Test
    void 직선_경로_생성_테스트() {
        // given
        Position current = new Position(0, 0);
        EnumSet<Direction> directions = EnumSet.of(Direction.S);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        Path southPath = paths.iterator().next();
        List<Position> positions = MoveStrategyTestHelper.toList(southPath);
        assertThat(positions).hasSize(9);
    }

    @DisplayName("경로에 다리가 하나도 없는 경우, 이동할 수 없다. - determineDestinations()")
    @Test
    void 다리_없는_경우_이동_불가_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(
                List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0)));
        Map<Position, Piece> boardState = new HashMap<>(); // 빈 보드 (다리 없음)
        Piece me = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("포는 포를 다리로 삼아 넘을 수 없다. - determineDestinations()")
    @Test
    void 포_다리_사용_불가_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(
                List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 0), new Piece(Side.HAN, PieceType.CANNON, "1"));

        Piece me = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("다리를 넘은 후, 빈 칸들은 이동 가능하다. - determineDestinations()")
    @Test
    void 다리_너머_빈칸_이동_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(
                List.of(new Position(1, 0), new Position(2, 0), new Position(3, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 0), new Piece(Side.HAN, PieceType.SOLDIER, "1"));

        Piece me = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        // 다리(1, 0)는 이동 불가, 그 너머인 (2, 0)과 (3, 0)만 가능
        assertThat(destinations).containsExactly(new Position(2, 0), new Position(3, 0));
    }

    @DisplayName("다리를 넘은 후, 적군 포는 잡을 수 없다. - determineDestinations()")
    @Test
    void 적군_포_포획_불가_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(new Position(1, 0), new Position(2, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 0), new Piece(Side.HAN, PieceType.SOLDIER, "1"));
        boardState.put(new Position(2, 0), new Piece(Side.HAN, PieceType.CANNON, "1"));

        Piece me = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("다리를 넘은 후, 일반 적군 기물은 포획 가능하다. - determineDestinations()")
    @Test
    void 일반_적군_포획_테스트() {
        // given
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(new Position(1, 0), new Position(2, 0)));
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 0), new Piece(Side.HAN, PieceType.SOLDIER, "1")); // 다리
        boardState.put(new Position(2, 0), new Piece(Side.HAN, PieceType.CHARIOT, "1")); // 적군 차

        Piece me = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, me);

        // then
        assertThat(destinations).containsExactly(new Position(2, 0));
    }

    @DisplayName("궁성 내에 기물이 있는 경우, 직진 방향으로 뛰어넘을 수 있다.")
    @Test
    void 궁성_내_기물_직진_뛰어넘기_테스트() {
        // given
        Position current = new Position(2, 4);
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 4), new Piece(Side.HAN, PieceType.SOLDIER, "1")); // 다리

        Piece movingPiece = new Piece(Side.CHO, PieceType.CANNON, "1");
        EnumSet<Direction> directions = EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);
        List<Position> destinations = strategy.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).containsExactly(new Position(0, 4));
    }

    @DisplayName("궁성 내에 기물이 있는 경우, 대각선 방향으로 뛰어넘을 수 있다.")
    @Test
    void 궁성_내_기물_대각선_뛰어넘기_테스트() {
        // given
        Position current = new Position(2, 5);
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(1, 4), new Piece(Side.HAN, PieceType.SOLDIER, "1")); // 다리

        Piece movingPiece = new Piece(Side.CHO, PieceType.CANNON, "1");
        EnumSet<Direction> directions = EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);
        List<Position> destinations = strategy.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).containsExactly(new Position(0, 3));
    }
}
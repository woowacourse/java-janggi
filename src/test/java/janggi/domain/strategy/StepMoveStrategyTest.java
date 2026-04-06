package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StepMoveStrategyTest {
    private final MoveStrategy strategy = new StepMoveStrategy();

    @DisplayName("한 칸 이동 시 보드 범위를 벗어나는 경우, 경로를 생성하지 않는다. - findMovablePaths()")
    @Test
    void 보드_범위_밖_경로_미생성_테스트() {
        // given
        // (0, 0) 위치에서 북쪽으로 한 칸 이동 시도
        Position current = new Position(0, 0);
        EnumSet<Direction> directions = EnumSet.of(Direction.N);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        assertThat(paths.iterator().hasNext()).isFalse();
    }

    @DisplayName("정상 범위인 경우, 지정된 방향으로 딱 한 칸의 좌표만 생성한다. - findMovablePaths()")
    @Test
    void 한_칸_경로_생성_테스트() {
        // given
        Position current = new Position(3, 3);
        EnumSet<Direction> directions = EnumSet.of(Direction.S);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        Path southPath = paths.iterator().next();
        List<Position> positions = MoveStrategyTestHelper.toList(southPath);

        assertThat(positions)
                .hasSize(1)
                .containsExactly(new Position(4, 3));
    }

    @DisplayName("목적지가 비어있는 경우, 이동 가능하다. - determineDestinations()")
    @Test
    void 빈_칸_이동_가능_테스트() {
        // given
        Position dest = new Position(4, 3);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(dest));
        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = new Piece(Side.CHO, PieceType.SOLDIER, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, movingPiece);

        // then
        assertThat(destinations).containsExactly(dest);
    }

    @DisplayName("목적지에 적군이 있는 경우, 포획 가능하다. - determineDestinations()")
    @Test
    void 적군_존재_시_이동_가능_테스트() {
        // given
        Position dest = new Position(4, 3);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.HAN, PieceType.SOLDIER, "1")); // 적군
        Piece movingPiece = new Piece(Side.CHO, PieceType.SOLDIER, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, movingPiece);

        // then
        assertThat(destinations).containsExactly(dest);
    }

    @DisplayName("목적지에 아군이 있는 경우, 이동할 수 없다. - determineDestinations()")
    @Test
    void 아군_존재_시_이동_불가_테스트() {
        // given
        Position dest = new Position(4, 3);
        Paths routes = MoveStrategyTestHelper.createRoute(List.of(dest));

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(dest, new Piece(Side.CHO, PieceType.SOLDIER, "2")); // 아군
        Piece movingPiece = new Piece(Side.CHO, PieceType.SOLDIER, "1");

        // when
        List<Position> destinations = strategy.determineDestinations(routes, boardState, movingPiece);

        // then
        assertThat(destinations).isEmpty();
    }
}
package strategy.move;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class KingAndGuardMoveStrategyTest {
    
    @Test
    void 왕과_사는_장애물이_없으면_지나갈수_있다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

        boolean canMove = strategy.canMove(route, List.of(), null, TeamColor.CHO);

        assertThat(canMove).isTrue();
    }

    @Test
    void 왕과_사는_장애물이_하나라도_있으면_지나갈수_없다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

        boolean canMove = strategy.canMove(
                route,
                List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                null,
                TeamColor.CHO);

        assertThat(canMove).isFalse();
    }

    @Test
    void 왕은_궁성_중앙에서_8방향으로_이동할_수_있다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Position curPos = Position.of(8, 4);
        Board router = new Board(Map.<Position, Piece>of());

        List<Route> routes = strategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.KING), router);
        assertThat(routes).containsExactlyInAnyOrder(
                new Route(curPos, Position.of(7, 4), List.of()),
                new Route(curPos, Position.of(9, 4), List.of()),
                new Route(curPos, Position.of(8, 3), List.of()),
                new Route(curPos, Position.of(8, 5), List.of()),
                new Route(curPos, Position.of(7, 3), List.of()),
                new Route(curPos, Position.of(7, 5), List.of()),
                new Route(curPos, Position.of(9, 3), List.of()),
                new Route(curPos, Position.of(9, 5), List.of()));
    }

    @Test
    void 사는_궁성_중앙에서_8방향으로_이동할_수_있다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Position curPos = Position.of(8, 4);
        Board router = new Board(Map.<Position, Piece>of());

        List<Route> routes = strategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.GUARD), router);
        assertThat(routes).containsExactlyInAnyOrder(
                new Route(curPos, Position.of(7, 4), List.of()),
                new Route(curPos, Position.of(9, 4), List.of()),
                new Route(curPos, Position.of(8, 3), List.of()),
                new Route(curPos, Position.of(8, 5), List.of()),
                new Route(curPos, Position.of(7, 3), List.of()),
                new Route(curPos, Position.of(7, 5), List.of()),
                new Route(curPos, Position.of(9, 3), List.of()),
                new Route(curPos, Position.of(9, 5), List.of()));
    }

    @Test
    void 왕은_궁성_밖에서는_이동경로가_없다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Position curPos = Position.of(5, 4);
        Board router = new Board(Map.<Position, Piece>of());

        List<Route> routes = strategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.KING), router);

        assertThat(routes).isEmpty();
    }

    @Test
    void 사는_궁성_밖에서는_이동경로가_없다() {
        MoveStrategy strategy = new KingAndGuardMoveStrategy();
        Position curPos = Position.of(5, 4);
        Board router = new Board(Map.<Position, Piece>of());

        List<Route> routes = strategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.GUARD), router);

        assertThat(routes).isEmpty();
    }
}

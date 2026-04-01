package strategy.move;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GuardMoveStrategyTest {

    @Test
    public void 사는_장애물이_없으면_지나갈수_있다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();
        Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

        boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);

        assertThat(canMove).isTrue();
    }

    @Test
    public void 사는_장애물이_하나라도_있으면_지나갈수_없다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();
        Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

        boolean canMove = moveStrategy.canMove(
                route,
                List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                null,
                TeamColor.CHO
        );

        assertThat(canMove).isFalse();
    }

    @Test
    public void 사는_궁성_중앙에서_8방향으로_이동할_수_있다() {
        Position curPos = Position.of(8, 4);
        MoveStrategy moveStrategy = new GuardMoveStrategy();

        List<Route> routes = moveStrategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.GUARD));
        assertThat(routes).containsExactlyInAnyOrder(
                new Route(curPos, Position.of(7, 4), List.of()),
                new Route(curPos, Position.of(9, 4), List.of()),
                new Route(curPos, Position.of(8, 3), List.of()),
                new Route(curPos, Position.of(8, 5), List.of()),
                new Route(curPos, Position.of(7, 3), List.of()),
                new Route(curPos, Position.of(7, 5), List.of()),
                new Route(curPos, Position.of(9, 3), List.of()),
                new Route(curPos, Position.of(9, 5), List.of())
        );
    }

    @Test
    public void 사는_궁성_밖에서는_이동경로가_없다() {
        Position curPos = Position.of(5, 4);
        MoveStrategy moveStrategy = new GuardMoveStrategy();

        List<Route> routes = moveStrategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.GUARD));

        assertThat(routes).isEmpty();
    }
}

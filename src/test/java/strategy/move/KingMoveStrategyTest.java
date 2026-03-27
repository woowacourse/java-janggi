package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KingMoveStrategyTest {

    @Nested
    class 이동경로 {
        @Test
        public void 초나라_왕은_8방향으로_이동_가능하다() {
            MoveStrategy strategy = new KingMoveStrategy();
            List<MovePath> paths = strategy.getPaths(TeamColor.CHO);

            assertThat(paths).hasSize(8);
            assertThat(paths).containsExactlyInAnyOrder(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.SOUTH)),
                    new MovePath(List.of(Direction.EAST)),
                    new MovePath(List.of(Direction.WEST)),
                    new MovePath(List.of(Direction.NORTH_EAST)),
                    new MovePath(List.of(Direction.NORTH_WEST)),
                    new MovePath(List.of(Direction.SOUTH_EAST)),
                    new MovePath(List.of(Direction.SOUTH_WEST))
            );
        }

        @Test
        public void 한나라_왕은_8방향으로_이동_가능하다() {
            MoveStrategy strategy = new KingMoveStrategy();
            List<MovePath> paths = strategy.getPaths(TeamColor.HAN);

            assertThat(paths).hasSize(8);
            assertThat(paths).containsExactlyInAnyOrder(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.SOUTH)),
                    new MovePath(List.of(Direction.EAST)),
                    new MovePath(List.of(Direction.WEST)),
                    new MovePath(List.of(Direction.NORTH_EAST)),
                    new MovePath(List.of(Direction.NORTH_WEST)),
                    new MovePath(List.of(Direction.SOUTH_EAST)),
                    new MovePath(List.of(Direction.SOUTH_WEST))
            );
        }
    }

    @Nested
    class 차단검사 {
        @Test
        public void 왕은_장애물이_없으면_지나갈수_있다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

            boolean canMove = moveStrategy.canMove(route, List.of(), Optional.empty(), TeamColor.CHO);

            assertThat(canMove).isTrue();
        }

        @Test
        public void 왕은_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(1, 4), Position.of(0, 4), List.of());

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                    Optional.empty(),
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }
    }

    @Nested
    class 좌표생성 {
        @Test
        public void 왕이_정상적으로_진행경로_좌표를_안다() {
            Position curPos = Position.of(1, 4);
            MoveStrategy moveStrategy = new KingMoveStrategy();

            List<Route> routes = moveStrategy.makeRoutes(curPos, TeamColor.CHO);
            assertThat(routes).containsExactlyInAnyOrder(
                    new Route(curPos, Position.of(0, 4), List.of()),
                    new Route(curPos, Position.of(2, 4), List.of()),
                    new Route(curPos, Position.of(1, 3), List.of()),
                    new Route(curPos, Position.of(1, 5), List.of()),
                    new Route(curPos, Position.of(0, 3), List.of()),
                    new Route(curPos, Position.of(0, 5), List.of()),
                    new Route(curPos, Position.of(2, 3), List.of()),
                    new Route(curPos, Position.of(2, 5), List.of())
            );
        }
    }
}

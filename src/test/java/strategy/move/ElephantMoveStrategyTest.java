package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import domain.palace.PalaceRouter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantMoveStrategyTest {

    private PalaceRouter outsidePalaceRouter() {
        return new PalaceRouter() {
            @Override
            public boolean isInsidePalace(Position position) {
                return false;
            }

            @Override
            public List<Position> getDiagonalAdjacents(Position position) {
                return List.of();
            }
        };
    }

    @Nested
    class 이동경로 {
        @Test
        public void 초나라_상은_직진1칸_대각선2칸으로_이루어진_8개의_경로를_가진다() {
            MoveStrategy strategy = new ElephantMoveStrategy();
            Position from = Position.of(4, 4);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> paths = strategy.getPaths(Piece.of(TeamColor.CHO, PieceType.ELEPHANT), from, router);

            assertThat(paths).hasSize(8);
            assertThat(paths).contains(
                    new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
                    new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
                    new MovePath(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST))
            );
        }

        @Test
        public void 한나라_상은_직진1칸_대각선2칸으로_이루어진_8개의_경로를_가진다() {
            MoveStrategy strategy = new ElephantMoveStrategy();
            Position from = Position.of(4, 4);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> paths = strategy.getPaths(Piece.of(TeamColor.HAN, PieceType.ELEPHANT), from, router);

            assertThat(paths).hasSize(8);
            assertThat(paths).contains(
                    new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
                    new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
                    new MovePath(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST))
            );
        }
    }

    @Nested
    class 차단검사 {
        @Test
        public void 상은_장애물이_없으면_지나갈수_있다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 2), List.of(Position.of(3, 4), Position.of(2, 3)));

            boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);

            assertThat(canMove).isTrue();
        }

        @Test
        public void 상은_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 2), List.of(Position.of(3, 4), Position.of(2, 3)));

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                    null,
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }
    }

    @Nested
    class 좌표생성 {
        @Test
        public void 상이_정상적으로_진행경로_좌표를_안다() {
            Position curPos = Position.of(4, 4);
            MoveStrategy moveStrategy = new ElephantMoveStrategy();
            PalaceRouter router = outsidePalaceRouter();

            List<Route> routes = moveStrategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.ELEPHANT), router);
            assertThat(routes).containsExactlyInAnyOrder(
                    new Route(curPos, Position.of(1, 2), List.of(Position.of(3, 4), Position.of(2, 3))),
                    new Route(curPos, Position.of(1, 6), List.of(Position.of(3, 4), Position.of(2, 5))),
                    new Route(curPos, Position.of(7, 2), List.of(Position.of(5, 4), Position.of(6, 3))),
                    new Route(curPos, Position.of(7, 6), List.of(Position.of(5, 4), Position.of(6, 5))),
                    new Route(curPos, Position.of(2, 1), List.of(Position.of(4, 3), Position.of(3, 2))),
                    new Route(curPos, Position.of(6, 1), List.of(Position.of(4, 3), Position.of(5, 2))),
                    new Route(curPos, Position.of(2, 7), List.of(Position.of(4, 5), Position.of(3, 6))),
                    new Route(curPos, Position.of(6, 7), List.of(Position.of(4, 5), Position.of(5, 6)))
            );
        }
    }
}

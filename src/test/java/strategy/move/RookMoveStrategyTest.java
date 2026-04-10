package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.Board;
import domain.palace.PalaceRouter;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RookMoveStrategyTest {

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
        public void 차는_초나라에서_동서남북_직선_경로를_보드_끝까지_가진다() {
            MoveStrategy strategy = new RookMoveStrategy();
            Position from = Position.of(4, 4);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> paths = strategy.getPaths(Piece.of(TeamColor.CHO, PieceType.ROOK), from, router);

            assertThat(paths).hasSize(36);
            assertThat(paths).contains(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH)),
                    new MovePath(List.of(Direction.SOUTH)),
                    new MovePath(List.of(Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH)),
                    new MovePath(List.of(Direction.EAST)),
                    new MovePath(List.of(Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST)),
                    new MovePath(List.of(Direction.WEST)),
                    new MovePath(List.of(Direction.WEST, Direction.WEST, Direction.WEST, Direction.WEST))
            );
        }

        @Test
        public void 차는_현재위치에서_여러칸_떨어진_직선_목적지_경로를_생성한다() {
            MoveStrategy strategy = new RookMoveStrategy();
            Position from = Position.of(4, 4);
            PalaceRouter router = outsidePalaceRouter();
            List<Route> routes = strategy.makeRoutes(from, Piece.of(TeamColor.HAN, PieceType.ROOK), router);

            assertThat(routes).contains(
                    new Route(Position.of(4, 4), Position.of(0, 4),
                            List.of(Position.of(3, 4), Position.of(2, 4), Position.of(1, 4))),
                    new Route(Position.of(4, 4), Position.of(4, 8),
                            List.of(Position.of(4, 5), Position.of(4, 6), Position.of(4, 7))),
                    new Route(Position.of(4, 4), Position.of(4, 0),
                            List.of(Position.of(4, 3), Position.of(4, 2), Position.of(4, 1)))
            );
        }

        @Test
        public void 차는_궁성_중심에서_대각선_한칸_경로를_가진다() {
            MoveStrategy strategy = new RookMoveStrategy();
            PalaceRouter router = new Board(Map.<Position, Piece>of());

            Position from = Position.of(1, 4);
            List<Route> routes = strategy.makeRoutes(from, Piece.of(TeamColor.HAN, PieceType.ROOK), router);

            assertThat(routes).contains(
                    new Route(from, Position.of(0, 3), List.of()),
                    new Route(from, Position.of(0, 5), List.of()),
                    new Route(from, Position.of(2, 3), List.of()),
                    new Route(from, Position.of(2, 5), List.of())
            );
        }

        @Test
        public void 차는_궁성_대각선_모서리에서_중앙을_거쳐_맞은편_모서리로_이동한다() {
            MoveStrategy strategy = new RookMoveStrategy();
            PalaceRouter router = new Board(Map.<Position, Piece>of());

            Position from = Position.of(0, 3);
            Position center = Position.of(1, 4);
            List<Route> routes = strategy.makeRoutes(from, Piece.of(TeamColor.HAN, PieceType.ROOK), router);

            assertThat(routes).contains(
                    new Route(from, Position.of(2, 5), List.of(center))
            );
        }
    }

    @Nested
    class 차단검사 {
        @Test
        public void 차는_장애물이_없으면_지나갈수_있다(){
            MoveStrategy moveStrategy = new RookMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);

            assertThat(canMove).isTrue();
        }

        @Test
        public void 차는_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new RookMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                    null,
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }

        @Test
        public void 차는_도착지에_같은팀_기물이_있으면_이동할수_없다() {
            MoveStrategy moveStrategy = new RookMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(),
                    Piece.of(TeamColor.CHO, PieceType.GUARD),
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }
    }
}

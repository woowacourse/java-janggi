package strategy.move;

import domain.Board;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Map;
import domain.palace.PalaceRouter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonMoveStrategyTest {

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
        public void 포는_초나라에서_동서남북_직선_경로를_보드_끝까지_가진다() {
            MoveStrategy strategy = new CannonMoveStrategy();
            Position from = Position.of(4, 4);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> paths = strategy.getPaths(Piece.of(TeamColor.CHO, PieceType.CANNON), from, router);

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
        public void 포는_현재위치에서_여러칸_떨어진_직선_목적지_경로를_생성한다() {
            MoveStrategy strategy = new CannonMoveStrategy();
            PalaceRouter router = outsidePalaceRouter();
            List<Route> routes = strategy.makeRoutes(
                    Position.of(4, 4),
                    Piece.of(TeamColor.HAN, PieceType.CANNON),
                    router
            );

            assertThat(routes).contains(
                    new Route(Position.of(4, 4), Position.of(0, 4),
                            List.of(Position.of(3, 4), Position.of(2, 4), Position.of(1, 4))),
                    new Route(Position.of(4, 4), Position.of(4, 8),
                            List.of(Position.of(4, 5), Position.of(4, 6), Position.of(4, 7))),
                    new Route(Position.of(4, 4), Position.of(8, 4),
                            List.of(Position.of(5, 4), Position.of(6, 4), Position.of(7, 4)))
            );
        }

        @Test
        public void 포는_궁성_중심에서_대각선_한칸_경로를_가진다() {
            MoveStrategy strategy = new CannonMoveStrategy();
            PalaceRouter router = new Board(Map.<Position, Piece>of());

            Position from = Position.of(1, 4);
            List<Route> routes = strategy.makeRoutes(from, Piece.of(TeamColor.HAN, PieceType.CANNON), router);

            assertThat(routes).contains(
                    new Route(from, Position.of(0, 3), List.of()),
                    new Route(from, Position.of(0, 5), List.of()),
                    new Route(from, Position.of(2, 3), List.of()),
                    new Route(from, Position.of(2, 5), List.of())
            );
        }

        @Test
        public void 포는_궁성_대각선_모서리에서_중앙을_거쳐_맞은편_모서리로_이동한다() {
            MoveStrategy strategy = new CannonMoveStrategy();
            PalaceRouter router = new Board(Map.<Position, Piece>of());

            Position from = Position.of(0, 3);
            Position center = Position.of(1, 4);
            List<Route> routes = strategy.makeRoutes(from, Piece.of(TeamColor.HAN, PieceType.CANNON), router);

            assertThat(routes).contains(
                    new Route(from, Position.of(2, 5), List.of(center))
            );
        }
    }

    @Nested
    class 차단검사 {
        @Test
        public void 포는_다리가_되는_기물이_하나도_없으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);
            assertThat(canMove).isFalse();
        }

        @Test
        public void 포는_다리가_되는_기물이_포이면_지나갈수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();
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
        public void 포는_다리가_되는_기물이_둘_이상이면_지나갈수_없다() {
            MoveStrategy moveStrategy = new CannonMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(0, 4), List.of(Position.of(3, 4), Position.of(2, 4), Position.of(1, 4)));

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(
                            Piece.of(TeamColor.CHO, PieceType.PAWN),
                            Piece.of(TeamColor.HAN, PieceType.HORSE)
                    ),
                    null,
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }

        @Test
        public void 포는_도착지에_상대방의_포가_있으면_잡을수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgePawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Piece targetCannon = Piece.of(TeamColor.HAN, PieceType.CANNON);

            boolean canMove = moveStrategy.canMove(
                    new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4))),
                    List.of(bridgePawn),
                    targetCannon,
                    TeamColor.CHO
            );
            assertThat(canMove).isFalse();
        }

        @Test
        public void 포는_도착지에_같은팀_기물이_있으면_이동할수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgeRook = Piece.of(TeamColor.HAN, PieceType.ROOK);
            Piece targetHorse = Piece.of(TeamColor.CHO, PieceType.HORSE);

            boolean canMove = moveStrategy.canMove(
                    new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4))),
                    List.of(bridgeRook),
                    targetHorse,
                    TeamColor.CHO
            );
            assertThat(canMove).isFalse();
        }

        @Test
        public void 포는_일반_다리를_넘어_빈칸으로_정상적으로_이동가능하다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgePawn = Piece.of(TeamColor.CHO, PieceType.PAWN);

            boolean canMove = moveStrategy.canMove(
                    new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4))),
                    List.of(bridgePawn),
                    null,
                    TeamColor.CHO
            );
            assertThat(canMove).isTrue();
        }

        @Test
        public void 포는_일반_다리를_넘어_도착지에_있는_적군기물을_포획가능하다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgeHorse = Piece.of(TeamColor.CHO, PieceType.HORSE);
            Piece targetRook = Piece.of(TeamColor.HAN, PieceType.ROOK);

            boolean canMove = moveStrategy.canMove(
                    new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4))),
                    List.of(bridgeHorse),
                    targetRook,
                    TeamColor.CHO
            );
            assertThat(canMove).isTrue();
        }

        @Test
        public void 포는_한칸_이동처럼_중간기물이_없는_경로로는_이동할수_없다() {
            MoveStrategy moveStrategy = new CannonMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);

            assertThat(canMove).isFalse();
        }
    }
}

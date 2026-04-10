package strategy.move;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.PalaceRouter;

public class PawnMoveStrategyTest {

    public Piece piece;

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

    @BeforeEach
    public void setUp() {
        piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
    }

    @Nested
    class 이동경로 {
        @Test
        public void 초나라_졸은_북동서로_이동_가능하다() {
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Position from = Position.of(2, 3);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> movePathList = moveStrategy.getPaths(Piece.of(TeamColor.CHO, PieceType.PAWN), from, router);
            assertThat(movePathList).contains(new MovePath(List.of(Direction.NORTH)));
            assertThat(movePathList).contains(new MovePath(List.of(Direction.WEST)));
            assertThat(movePathList).contains(new MovePath(List.of(Direction.EAST)));
            assertThat(movePathList).doesNotContain(new MovePath(List.of(Direction.SOUTH)));
        }

        @Test
        public void 한나라_졸은_남동서로_이동_가능하다() {
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Position from = Position.of(2, 3);
            PalaceRouter router = outsidePalaceRouter();
            List<MovePath> movePaths = moveStrategy.getPaths(Piece.of(TeamColor.HAN, PieceType.PAWN), from, router);
            assertThat(movePaths).contains(new MovePath(List.of(Direction.SOUTH)));
            assertThat(movePaths).contains(new MovePath(List.of(Direction.WEST)));
            assertThat(movePaths).contains(new MovePath(List.of(Direction.EAST)));
            assertThat(movePaths).doesNotContain(new MovePath(List.of(Direction.NORTH)));
        }
    }

    @Nested
    class 좌표생성 {
        @Test
        public void 초나라_졸이_정상적으로_진행경로_좌표를_안다(){
            Position curPos = Position.of(2,3);
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            PalaceRouter router = outsidePalaceRouter();

            List<Route> routes = moveStrategy.makeRoutes(curPos, Piece.of(TeamColor.CHO, PieceType.PAWN), router);
            assertThat(routes).containsExactlyInAnyOrder(
                    new Route(curPos, Position.of(1, 3), List.of()),
                    new Route(curPos, Position.of(2, 4), List.of()),
                    new Route(curPos, Position.of(2, 2), List.of())
            );
        }

        @Test
        public void 한나라_졸은_현재위치와_이동방향을_기반으로_이동가능한_좌표들_구한다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Position curPos = Position.of(3,4);
            PalaceRouter router = outsidePalaceRouter();

            List<Route> possibleRoutes = moveStrategy.makeRoutes(curPos, Piece.of(TeamColor.HAN, PieceType.PAWN), router);
            assertThat(possibleRoutes).containsExactlyInAnyOrder(
                    new Route(curPos,Position.of(4,4),List.of()),
                    new Route(curPos,Position.of(3,5),List.of()),
                    new Route(curPos,Position.of(3,3),List.of())
            );
        }
    }

    @Nested
    class 차단검사 {
        @Test
        public void 졸은_장애물이_없으면_지나갈수_있다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(route, List.of(), null, TeamColor.CHO);

            assertThat(canMove).isTrue();
        }

        @Test
        public void 졸은_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO,PieceType.CANNON)),
                    null,
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }
    }
}

package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnMoveStrategyTest {

    public Piece piece;

    @BeforeEach
    public void setUp() {
        piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
    }

    @Nested
    class 이동경로 {
        @Test
        public void 초나라_졸은_북동서로_이동_가능하다() {
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            List<MovePath> movePathList = moveStrategy.getPaths(TeamColor.CHO);
            assertThat(movePathList).contains(new MovePath(List.of(Direction.NORTH)));
            assertThat(movePathList).contains(new MovePath(List.of(Direction.WEST)));
            assertThat(movePathList).contains(new MovePath(List.of(Direction.EAST)));
            assertThat(movePathList).doesNotContain(new MovePath(List.of(Direction.SOUTH)));
        }

        @Test
        public void 한나라_졸은_남동서로_이동_가능하다() {
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            List<MovePath> movePaths = moveStrategy.getPaths(TeamColor.HAN);
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
            Position curPos = Position.of(4, 4);
            MoveStrategy moveStrategy = new PawnMoveStrategy();

            List<Route> routes = moveStrategy.makeRoutes(curPos, TeamColor.CHO);
            assertThat(routes).containsExactlyInAnyOrder(
                    new Route(curPos, Position.of(3, 4), List.of()),
                    new Route(curPos, Position.of(4, 5), List.of()),
                    new Route(curPos, Position.of(4, 3), List.of())
            );
        }

        @Test
        public void 한나라_졸은_현재위치와_이동방향을_기반으로_이동가능한_좌표들_구한다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Position curPos = Position.of(3,4);

            List<Route> possibleRoutes = moveStrategy.makeRoutes(curPos, TeamColor.HAN);
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

            boolean canMove = moveStrategy.canMove(route, List.of(), Optional.empty());

            assertThat(canMove).isTrue();
        }

        @Test
        public void 졸은_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO,PieceType.CANNON)),
                    Optional.empty()
            );

            assertThat(canMove).isFalse();
        }
    }
}


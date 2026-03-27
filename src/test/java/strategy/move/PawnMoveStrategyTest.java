package strategy.move;

import domain.BlockingPieces;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PawnMoveStrategyTest {

    public Piece piece;

    @BeforeEach
    public void setUp() {
        piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
    }


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


    @Test
    public void 초나라_졸이_정상적으로_진행경로_좌표를_안다(){
        Position curPos = Position.of(2,3);
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Route> routes = moveStrategy.makeRoutes(curPos, TeamColor.CHO);
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

        List<Route> possibleRoutes = moveStrategy.makeRoutes(curPos, TeamColor.HAN);
        assertThat(possibleRoutes).containsExactlyInAnyOrder(
                new Route(curPos,Position.of(4,4),List.of()),
                new Route(curPos,Position.of(3,5),List.of()),
                new Route(curPos,Position.of(3,3),List.of())
        );
    }

    @Test
    public void 졸은_장애물이_없으면_지나갈수_있다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blocking = List.of();
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isTrue();
    }

    @Test
    public void 졸은_장애물이_하나라도_있으면_지나갈수_없다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blocking = List.of(Piece.of(TeamColor.CHO,PieceType.CANNON));
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isFalse();
    }


}

package strategy.move;

import domain.BlockingPieces;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElephantMoveStrategyTest {

    @Test
    public void 초나라_상은_직진1칸_대각선2칸으로_이루어진_8개의_경로를_가진다() {
        MoveStrategy strategy = new ElephantMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.CHO);

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
        List<MovePath> paths = strategy.getPaths(TeamColor.HAN);

        assertThat(paths).hasSize(8);
        assertThat(paths).contains(
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST))
        );
    }

    @Test
    public void 상은_장애물이_없으면_지나갈수_있다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blocking = List.of();
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isTrue();
    }

    @Test
    public void 상은_장애물이_하나라도_있으면_지나갈수_없다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();
        List<Piece> blocking = List.of(Piece.of(TeamColor.CHO, PieceType.CANNON));
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isFalse();
    }
}
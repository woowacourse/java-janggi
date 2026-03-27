package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorseMoveStrategyTest {

    @Test
    public void 초나라_마는_직진1칸_대각선1칸으로_이루어진_8개의_경로를_가진다() {
        MoveStrategy strategy = new HorseMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.CHO);

        assertThat(paths).hasSize(8);
        assertThat(paths).contains(
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.EAST, Direction. NORTH_EAST)),
                new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.WEST, Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.WEST, Direction.SOUTH_WEST))
        );
    }

    @Test
    public void 한나라_마는_직진1칸_대각선1칸으로_이루어진_8개의_경로를_가진다() {
        MoveStrategy strategy = new HorseMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.HAN);

        assertThat(paths).hasSize(8);
        assertThat(paths).contains(
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                new MovePath(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.EAST, Direction. NORTH_EAST)),
                new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                new MovePath(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.WEST, Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.WEST, Direction.SOUTH_WEST))
        );
    }

    @Test
    public void 마는_장애물이_없으면_지나갈수_있다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blockingPieces = List.of();
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces);

        assertThat(canJumpTo).isTrue();
    }

    @Test
    public void 마는_장애물이_하나라도_있으면_지나갈수_없다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blockingPieces = List.of(Piece.of(TeamColor.CHO, PieceType.CANNON));
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces);

        assertThat(canJumpTo).isFalse();
    }
}
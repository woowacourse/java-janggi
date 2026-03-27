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

public class RookMoveStrategyTest {

    @Test
    public void 차는_초나라에서_동서남북_4방향을_가진다() {
        MoveStrategy strategy = new RookMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.CHO);

        assertThat(paths).hasSize(4);
        assertThat(paths).containsExactlyInAnyOrder(
                new MovePath(List.of(Direction.NORTH)),
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST))
        );
    }

    @Test
    public void 차는_한나라에서_동서남북_4방향을_가진다() {
        MoveStrategy strategy = new RookMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.HAN);

        assertThat(paths).hasSize(4);
        assertThat(paths).containsExactlyInAnyOrder(
                new MovePath(List.of(Direction.NORTH)),
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST))
        );
    }

    @Test
    public void 차는_장애물이_없으면_지나갈수_있다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blocking = List.of();
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isTrue();
    }

    @Test
    public void 차는_장애물이_하나라도_있으면_지나갈수_없다(){
        MoveStrategy moveStrategy = new PawnMoveStrategy();

        List<Piece> blocking = List.of(Piece.of(TeamColor.CHO, PieceType.CANNON));
        BlockingPieces blockingPieces = new BlockingPieces(blocking);
        boolean canJumpTo  = moveStrategy.canJump(blockingPieces, TeamColor.CHO);

        assertThat(canJumpTo).isFalse();
    }
}

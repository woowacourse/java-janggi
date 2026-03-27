package strategy.move;

import domain.BlockingPieces;
import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonMoveStrategyTest {

    @Nested
    class 이동경로 {
        @Test
        public void 포는_초나라에서_동서남북_4방향을_가진다() {
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
        public void 포는_한나라에서_동서남북_4방향을_가진다() {
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
    }

    @Nested
    class 차단검사 {
        @Test
        public void 포는_다리가_되는_기물이_하나도_없으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            List<Piece> blocking = List.of();
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isFalse();
        }

        @Test
        public void 포는_다리가_되는_기물이_포이면_지나갈수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            List<Piece> blocking = List.of(Piece.of(TeamColor.CHO, PieceType.CANNON));
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isFalse();
        }

        @Test
        public void 포는_도착지에_상대방의_포가_있으면_잡을수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgePawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Piece targetCannon = Piece.of(TeamColor.HAN, PieceType.CANNON);

            List<Piece> blocking = List.of(bridgePawn, targetCannon);
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isFalse();
        }

        @Test
        public void 포는_도착지에_같은팀_기물이_있으면_이동할수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgeRook = Piece.of(TeamColor.HAN, PieceType.ROOK);
            Piece targetHorse = Piece.of(TeamColor.CHO, PieceType.HORSE);

            List<Piece> blocking = List.of(bridgeRook, targetHorse);
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isFalse();
        }

        @Test
        public void 포는_일반_다리를_넘어_빈칸으로_정상적으로_이동가능하다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgePawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            List<Piece> blocking = List.of(bridgePawn);
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isTrue();
        }

        @Test
        public void 포는_일반_다리를_넘어_도착지에_있는_적군기물을_포획가능하다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();

            Piece bridgeHorse = Piece.of(TeamColor.CHO, PieceType.HORSE);
            Piece targetRook = Piece.of(TeamColor.HAN, PieceType.ROOK);

            List<Piece> blocking = List.of(bridgeHorse, targetRook);
            BlockingPieces blockingPieces = new BlockingPieces(blocking);

            boolean canJumpTo = moveStrategy.canJump(blockingPieces, TeamColor.CHO);
            assertThat(canJumpTo).isTrue();
        }
    }
}

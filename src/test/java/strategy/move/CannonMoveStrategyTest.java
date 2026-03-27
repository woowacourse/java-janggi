package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Optional;
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
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(route, List.of(), Optional.empty(), TeamColor.CHO);
            assertThat(canMove).isFalse();
        }

        @Test
        public void 포는_다리가_되는_기물이_포이면_지나갈수_없다(){
            MoveStrategy moveStrategy = new CannonMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(1, 4), List.of(Position.of(3, 4), Position.of(2, 4)));

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                    Optional.empty(),
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
                    Optional.of(targetCannon),
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
                    Optional.of(targetHorse),
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
                    Optional.empty(),
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
                    Optional.of(targetRook),
                    TeamColor.CHO
            );
            assertThat(canMove).isTrue();
        }
    }
}

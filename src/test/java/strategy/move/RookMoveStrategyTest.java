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

public class RookMoveStrategyTest {

    @Nested
    class 이동경로 {
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
    }

    @Nested
    class 차단검사 {
        @Test
        public void 차는_장애물이_없으면_지나갈수_있다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(route, List.of(), Optional.empty(), TeamColor.CHO);

            assertThat(canMove).isTrue();
        }

        @Test
        public void 차는_장애물이_하나라도_있으면_지나갈수_없다(){
            MoveStrategy moveStrategy = new PawnMoveStrategy();
            Route route = new Route(Position.of(4, 4), Position.of(3, 4), List.of());

            boolean canMove = moveStrategy.canMove(
                    route,
                    List.of(Piece.of(TeamColor.CHO, PieceType.CANNON)),
                    Optional.empty(),
                    TeamColor.CHO
            );

            assertThat(canMove).isFalse();
        }
    }
}

package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.board.Position;
import domain.board.Route;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveStrategyTest {

    @Nested
    class DefaultMakeRoutes {
        @Test
        void 이동_경로_스텝을_따라_Route를_생성한다() {
            MoveStrategy strategy = new TestMoveStrategy(List.of(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.EAST, Direction.EAST))
            ));

            List<Route> routes = strategy.makeRoutes(Position.of(4, 4), TeamColor.CHO);

            assertThat(routes).containsExactly(
                    new Route(Position.of(4, 4), Position.of(3, 4), List.of()),
                    new Route(Position.of(4, 4), Position.of(4, 6), List.of(Position.of(4, 5)))
            );
        }

        @Test
        void 보드_밖으로_나가는_경로는_제외한다() {
            MoveStrategy strategy = new TestMoveStrategy(List.of(
                    new MovePath(List.of(Direction.NORTH)),
                    new MovePath(List.of(Direction.EAST))
            ));

            List<Route> routes = strategy.makeRoutes(Position.of(0, 0), TeamColor.CHO);

            assertThat(routes).containsExactly(
                    new Route(Position.of(0, 0), Position.of(0, 1), List.of())
            );
        }
    }

    @Nested
    class DefaultCanMove {
        @Test
        void 중간_장애물이_있으면_이동할_수_없다() {
            MoveStrategy strategy = new TestMoveStrategy(List.of());

            boolean canMove = strategy.canMove(
                    new Route(Position.of(4, 4), Position.of(4, 5), List.of()),
                    List.of(Piece.of(TeamColor.CHO, PieceType.PAWN)),
                    Optional.empty()
            );

            assertThat(canMove).isFalse();
        }

        @Test
        void 중간_장애물이_없으면_도착지_상태와_무관하게_전략_공통_검사는_통과한다() {
            MoveStrategy strategy = new TestMoveStrategy(List.of());

            boolean canMove = strategy.canMove(
                    new Route(Position.of(4, 4), Position.of(4, 5), List.of()),
                    List.of(),
                    Optional.of(Piece.of(TeamColor.CHO, PieceType.GUARD))
            );

            assertThat(canMove).isTrue();
        }
    }

    private static class TestMoveStrategy implements MoveStrategy {
        private final List<MovePath> paths;

        private TestMoveStrategy(List<MovePath> paths) {
            this.paths = paths;
        }

        @Override
        public List<MovePath> getPaths(TeamColor teamColor) {
            return paths;
        }
    }
}

package domain.palace;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Position;
import domain.board.Route;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceRouteGeneratorTest {

    private final PalaceRouteGenerator palaceRouteGenerator = new PalaceRouteGenerator();

    @Nested
    class 궁과사의_궁성_이동 {
        @Test
        void 궁성_중앙에서는_연결된_모든_좌표로_이동_경로를_생성한다() {
            assertThat(palaceRouteGenerator.createRoutes(Position.of(8, 4), PieceType.KING, TeamColor.CHO))
                    .containsExactlyInAnyOrder(
                            new Route(Position.of(8, 4), Position.of(7, 3), List.of()),
                            new Route(Position.of(8, 4), Position.of(7, 4), List.of()),
                            new Route(Position.of(8, 4), Position.of(7, 5), List.of()),
                            new Route(Position.of(8, 4), Position.of(8, 3), List.of()),
                            new Route(Position.of(8, 4), Position.of(8, 5), List.of()),
                            new Route(Position.of(8, 4), Position.of(9, 3), List.of()),
                            new Route(Position.of(8, 4), Position.of(9, 4), List.of()),
                            new Route(Position.of(8, 4), Position.of(9, 5), List.of())
                    );
        }

        @Test
        void 궁성_꼭지점에서는_연결된_세_좌표로만_이동_경로를_생성한다() {
            assertThat(palaceRouteGenerator.createRoutes(Position.of(0, 3), PieceType.GUARD, TeamColor.HAN))
                    .containsExactlyInAnyOrder(
                            new Route(Position.of(0, 3), Position.of(0, 4), List.of()),
                            new Route(Position.of(0, 3), Position.of(1, 3), List.of()),
                            new Route(Position.of(0, 3), Position.of(1, 4), List.of())
                    );
        }

        @Test
        void 궁성_밖의_좌표에서는_이동_경로를_생성하지_않는다() {
            assertThat(palaceRouteGenerator.createRoutes(Position.of(4, 4), PieceType.KING, TeamColor.HAN))
                    .isEmpty();
        }
    }
}

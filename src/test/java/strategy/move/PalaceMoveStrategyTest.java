package strategy.move;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;

class PalaceMoveStrategyTest {

    @Test
    void 궁과_사는_궁성에서_연결된_좌표로_이동_경로를_생성한다() {
        final MoveStrategy moveStrategy = new PalaceMoveStrategy();

        assertThat(moveStrategy.makeRoutes(Position.of(0, 3), TeamColor.HAN))
                .containsExactlyInAnyOrder(
                        new Route(Position.of(0, 3), Position.of(0, 4), List.of()),
                        new Route(Position.of(0, 3), Position.of(1, 3), List.of()),
                        new Route(Position.of(0, 3), Position.of(1, 4), List.of())
                );
    }
}

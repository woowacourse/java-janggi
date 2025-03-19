package strategy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Position;
import piece.Route;

class MoveStrategyTest {
    @Test
    void 졸은_가능한_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 0);
        MoveStrategy moveStrategy = new JolMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition);

        // then
        List<Position> positions = route.getPositions();
        Assertions.assertThat(positions.size()).isEqualTo(1);
        Assertions.assertThat(positions.getFirst()).isEqualTo(endPosition);
    }
}

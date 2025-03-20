package object.strategy;

import java.util.ArrayList;
import object.strategy.MoveStrategy;
import object.strategy.SaMoveStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import object.piece.Pieces;
import object.Coordinate;
import object.piece.Team;

public class SaMoveStrategyTest {
    @Test
    void 아직_구현되지않음() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(1, 0);
        MoveStrategy moveStrategy = new SaMoveStrategy();
        Assertions.assertThatThrownBy(() -> moveStrategy.getLegalRoute(startCoordinate, endCoordinate))
                .isInstanceOf(IllegalStateException.class);
        Assertions.assertThatThrownBy(
                        () -> moveStrategy.move(new Coordinate(0, 5), new Pieces(new ArrayList<>()), Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
    }
}

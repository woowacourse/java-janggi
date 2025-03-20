package object.strategy;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import object.piece.Pieces;
import object.Coordinate;
import object.piece.Team;

public class GuardStrategyTest {
    @Test
    void 아직_구현되지않음() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(1, 0);
        MoveStrategy moveStrategy = new GuardStrategy();
        Assertions.assertThatThrownBy(() -> moveStrategy.getLegalRoute(startCoordinate, endCoordinate, Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
        Assertions.assertThatThrownBy(
                        () -> moveStrategy.move(new Coordinate(0, 5), new Pieces(new ArrayList<>()), Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
    }
}

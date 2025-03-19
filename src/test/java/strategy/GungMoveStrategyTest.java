package strategy;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Pieces;
import piece.Position;
import piece.Team;

public class GungMoveStrategyTest {
    @Test
    void 아직_구현되지않음() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 0);
        MoveStrategy moveStrategy = new GungMoveStrategy();
        Assertions.assertThatThrownBy(() -> moveStrategy.getLegalRoute(startPosition, endPosition))
                .isInstanceOf(IllegalStateException.class);
        Assertions.assertThatThrownBy(
                        () -> moveStrategy.move(new Position(0, 5), new Pieces(new ArrayList<>()), Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
    }
}

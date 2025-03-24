package move;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

class GungMoveBehaviorTest {
    @Test
    void 아직_구현되지않음() {
        MoveBehavior moveBehavior = new GungMoveBehavior();

        Assertions.assertThatThrownBy(
                        () -> moveBehavior.move(new Position(0, 5), new Pieces(new ArrayList<>()), Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
    }
}

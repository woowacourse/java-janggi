package move;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Pieces;
import piece.Team;
import piece.position.JanggiPosition;

class SaMoveBehaviorTest {
    @Test
    void 아직_구현되지않음() {
        JanggiMoveBehavior moveBehavior = new SaMoveBehavior();
        Assertions.assertThatThrownBy(
                        () -> moveBehavior.move(new JanggiPosition(0, 5), new Pieces(new ArrayList<>()), Team.BLUE))
                .isInstanceOf(IllegalStateException.class);
    }
}

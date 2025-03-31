package janggi.domain.piece.movement.normal.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.pieces.Pieces;
import java.util.Map;
import org.junit.jupiter.api.Test;

@ReplaceUnderBar
class GuardMovementStrategyTest {

    @Test
    void 가드는_움직임을_구현하지_않는다() {
        GuardMovementStrategy guardMovementStrategy = new GuardMovementStrategy();

        assertThat(guardMovementStrategy.isLegalDestination(null, null)).isFalse();
        assertThat(guardMovementStrategy.getAllPiecesOnPath(new Pieces(Map.of()), null, null).isEmpty()).isTrue();
    }
}

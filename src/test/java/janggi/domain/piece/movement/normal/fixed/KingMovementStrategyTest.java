package janggi.domain.piece.movement.normal.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.pieces.Pieces;
import java.util.Map;
import org.junit.jupiter.api.Test;

@ReplaceUnderBar
class KingMovementStrategyTest {

    @Test
    void 킹은_움직임을_구현하지_않는다() {
        KingMovementStrategy kingMovementStrategy = new KingMovementStrategy();

        assertThat(kingMovementStrategy.isLegalDestination(null, null)).isFalse();
        assertThat(kingMovementStrategy.getAllPiecesOnPath(new Pieces(Map.of()), null, null).isEmpty()).isTrue();
    }
}

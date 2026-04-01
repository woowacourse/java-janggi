package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.JolMoveStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JolTest {
    private static final Jol JOL_OF_CHO = new Jol(new JolMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

    @Test
    void 목적지와_출발지_사이에_상대_기물이_존재할_수_없으므로_잘_간다고_판단한다() {
        // given
        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        assertDoesNotThrow(() -> JOL_OF_CHO.movePolicy(pathContext));
    }
}
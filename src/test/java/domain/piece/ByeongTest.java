package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.ByeongMoveStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Test
    void 목적지와_출발지_사이에_상대_기물이_존재할_수_없으므로_잘_간다고_판단한다() {
        //given
        Byeong testByeong = new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        assertDoesNotThrow(() -> testByeong.movePolicy(pathContext));
    }
}
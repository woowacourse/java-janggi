package domain.piece;

import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.SlidingMoveStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {
    private static final Po TEST_PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO);
    private static final Po TARGET_PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.HAN);

    @Test
    void 포가_포를_잡으면_예외가_발생해야_한다() {
        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(TARGET_PO)).isInstanceOf(IllegalArgumentException.class);
    }
}
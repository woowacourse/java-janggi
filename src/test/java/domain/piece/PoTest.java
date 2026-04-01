package domain.piece;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {
    private static final Po TEST_PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO);
    private static final Po TARGET_PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.HAN);
    private static final Cha ANOTHER_PIECE = new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.HAN);

    @Test
    void 포가_포를_잡으면_예외가_발생해야_한다() {
        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(TARGET_PO)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_포가_있는_경우_예외가_발생해야_한다() {
        Position obstacle = Position.of(2, 4);
        PathContext pathContext = PathContext.from(Map.of(obstacle, TARGET_PO));

        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(pathContext))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_기물이_1개만_있는_경우_갈_수_있어야_한다() {
        //given
        Position obstacle = Position.of(2, 3);

        PathContext pathContext = PathContext.from(Map.of(obstacle, ANOTHER_PIECE));

        //when & then
        Assertions.assertThatNoException().isThrownBy(() -> TEST_PO.movePolicy(pathContext));
    }

    @Test
    void 목적지와_포_사이에_기물이_2개_이상인_경우_예외가_발생해야_한다() {
        //given
        Position firstObstacle = Position.of(2, 3);
        Position secondObstacle = Position.of(2, 4);

        PathContext pathContext = PathContext.from(Map.of(firstObstacle, ANOTHER_PIECE, secondObstacle, ANOTHER_PIECE));

        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(pathContext))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_기물이_없으면_예외가_발생해야_한다() {
        PathContext pathContext = PathContext.from(Map.of());

        //when, then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(pathContext))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포를_잡으면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(TARGET_PO)).isInstanceOf(IllegalArgumentException.class);
    }
}
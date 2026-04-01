package domain.piece;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.MaMoveStrategy;
import domain.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaTest {
    private static final Ma MA_OF_CHO = new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

    @Test
    void 목적지와_출발지_사이에_기물이_있으면_예외가_발생해야_한다() {
        //given
        Position obstacle = Position.of(3, 2);

        PathContext pathContext = PathContext.from(
                Map.of(obstacle, new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO)));

        //when & then
        Assertions.assertThatThrownBy(() -> MA_OF_CHO.movePolicy(pathContext))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 목적지와_출발지_사이에_기물이_없으면_이동할_수_있어야_한다() {
        //given

        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        Assertions.assertThatNoException().isThrownBy(() -> MA_OF_CHO.movePolicy(pathContext));
    }
}
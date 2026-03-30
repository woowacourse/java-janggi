package domain.piece;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangTest {
    private static final Piece SANG_OF_CHO = new Sang(new SangMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

    @Test
    void 목적지와_출발지_사이에_기물이_있으면_예외가_발생해야_한다() {
        //given
        Position obstacle = Position.of(4, 3);

        //when & then
        PathContext pathContext = PathContext.from(
                Map.of(obstacle, new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO)));

        //when & then
        Assertions.assertThatThrownBy(() -> SANG_OF_CHO.movePolicy(pathContext))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 목적지와_출발지_사이에_기물이_없으면_이동할_수_있어야_한다() {
        //given
        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        Assertions.assertThatNoException().isThrownBy(() -> SANG_OF_CHO.movePolicy(pathContext));
    }


    @ParameterizedTest
    @MethodSource("allyCanNotBeCaptured")
    void 아군_기물을_잡으면_예외가_발생한다(Piece target) {
        Assertions.assertThatThrownBy(() -> SANG_OF_CHO.capture(target)).isInstanceOf(IllegalArgumentException.class);

    }

    private static Stream<Arguments> allyCanNotBeCaptured() {
        return Stream.of(
                Arguments.of(new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.CHO)),
                Arguments.of(new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO)),
                Arguments.of(new Sang(new SangMoveStrategy(), new NormalMovementPolicy(), Team.CHO)),
                Arguments.of(new Jol(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO)),
                Arguments.of(new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO)),
                Arguments.of(new Sa(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO)),
                Arguments.of(new Jang(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO))
        );
    }

    @ParameterizedTest
    @MethodSource("enemyCanBeCaptured")
    void 상대_기물은_잡을_수_있다(Piece target) {
        Assertions.assertThatNoException().isThrownBy((() -> SANG_OF_CHO.capture(target)));
    }

    private static Stream<Arguments> enemyCanBeCaptured() {
        return Stream.of(
                Arguments.of(new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.HAN)),
                Arguments.of(new Sang(new SangMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Jol(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Sa(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Jang(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN))
        );
    }

}
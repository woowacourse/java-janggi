package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.PathContext;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JangTest {
    private static final Jang JANG_OF_CHO = new Jang(new SingleStepMoveStrategy(), new NormalMovementPolicy(),
            Team.CHO);

    @Test
    void 목적지와_출발지_사이에_상대_기물이_존재할_수_없으므로_잘_간다고_판단한다() {
        // given
        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        assertDoesNotThrow(() -> JANG_OF_CHO.movePolicy(pathContext));
    }

    @ParameterizedTest
    @MethodSource("allyCanNotBeCaptured")
    void 아군_기물을_잡으면_예외가_발생한다(Piece target) {
        Assertions.assertThatThrownBy(() -> JANG_OF_CHO.capture(target)).isInstanceOf(IllegalArgumentException.class);

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
        Assertions.assertThatNoException().isThrownBy((() -> JANG_OF_CHO.capture(target)));
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
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

    @ParameterizedTest
    @MethodSource("allyCanNotBeCaptured")
    void 아군_기물을_잡으면_예외가_발생한다(Piece target) {
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(target)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 포를_잡아도_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> TEST_PO.capture(TARGET_PO)).isInstanceOf(IllegalArgumentException.class);
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
        Assertions.assertThatNoException().isThrownBy((() -> TEST_PO.capture(target)));
    }

    private static Stream<Arguments> enemyCanBeCaptured() {
        return Stream.of(
                Arguments.of(new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Sang(new SangMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Jol(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Sa(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN)),
                Arguments.of(new Jang(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.HAN))
        );
    }

}
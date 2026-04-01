package domain.piece;

import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTest {
    @ParameterizedTest
    @MethodSource("allyCanNotBeCaptured")
    void 아군_기물을_잡으면_예외가_발생한다(Piece target) {
        Byeong testPiece = new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Assertions.assertThatThrownBy(() -> testPiece.capture(target)).isInstanceOf(IllegalArgumentException.class);

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
        Byeong testPiece = new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Assertions.assertThatNoException().isThrownBy((() -> testPiece.capture(target)));

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
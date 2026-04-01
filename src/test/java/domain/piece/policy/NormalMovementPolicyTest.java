package domain.piece.policy;


import domain.PathContext;
import domain.piece.Byeong;
import domain.piece.Sa;
import domain.piece.Team;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NormalMovementPolicyTest {
    @Test
    void 경로에_기물이_없으면_예외가_발생하지_않는다() {
        //given
        Byeong tsetPiece = new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

        PathContext pathContext = PathContext.from(Map.of());

        //when & then
        Assertions.assertThatNoException().isThrownBy(() -> tsetPiece.movePolicy(pathContext));
    }


    @Test
    void 경로에_기물이_있으면_예외가_발생해야_한다() {
        //given
        Byeong testPiece = new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

        PathContext pathContext = PathContext.from(Map.of(
                Position.of(1, 1), new Sa(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO)));

        //when & then
        Assertions.assertThatThrownBy(() -> testPiece.movePolicy(pathContext));
    }
}
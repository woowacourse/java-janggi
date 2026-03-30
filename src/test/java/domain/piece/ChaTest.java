package domain.piece;


import domain.BoardStatus;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ChaTest {
    @Test
    void 목적지와_출발지_사이에_기물이_있으면_예외가_발생해야_한다() {
        // given
        Cha testCha = new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);
        Position obstacle = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(obstacle, new Sa(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        // when & then
        Assertions.assertThatThrownBy(() -> testCha.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 목적지와_출발지_사이에_기물이_없다면_이동할_수_있어야_한다() {
        //given
        Cha testCha = new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        BoardStatus testBoard = BoardStatus.from(testPieces);

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> testCha.check(testBoard, start, destination));
    }

}
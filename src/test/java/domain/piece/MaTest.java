package domain.piece;

import domain.BoardStatus;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.MaMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaTest {
    @Test
    void 목적지와_출발지_사이에_기물이_있으면_예외가_발생해야_한다() {
        //given
        Ma testMa = new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(4, 3);
        Position obstacle = Position.of(3, 2);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(obstacle, new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
        Assertions.assertThatThrownBy(() -> testMa.check(testBoard, start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }


    @Test
    void 목적지와_출발지_사이에_기물이_없으면_이동할_수_있어야_한다() {
        //given
        Ma testMa = new Ma(new MaMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(4, 3);
        HashMap<Position, Piece> testPieces = new HashMap<>();

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
//        Assertions.assertThatNoException().isThrownBy(() -> testMa.check(testBoard, start, destination));
        Assertions.assertThat(testMa.check(testBoard, start, destination)).isTrue();
    }
}
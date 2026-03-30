package domain.piece;

import domain.BoardStatus;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {
    private static final Po TEST_PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO);

    @Test
    void 목적지에_상대방_포가_있는_경우_예외가_발생해야_한다() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);
        Position obstacle = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstacle, new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO));

        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(testPieces, TEST_PO.findMovablePath(start, destination)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_포가_있는_경우_예외가_발생해야_한다() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 7);
        Position obstacle = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstacle, new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(testPieces, TEST_PO.findMovablePath(start, destination)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_기물이_1개만_있는_경우_갈_수_있어야_한다() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        Position obstaclePosition = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(obstaclePosition, new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> TEST_PO.movePolicy(testPieces, TEST_PO.findMovablePath(start, destination)));
    }

    @Test
    void 목적지와_포_사이에_기물이_2개_이상인_경우_예외가_발생해야_한다() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 5);
        Position firstObstaclePosition = Position.of(2, 3);
        Position secondObstaclePosition = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(start, TEST_PO);
        testPieces.put(firstObstaclePosition,
                new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO));
        testPieces.put(secondObstaclePosition,
                new Byeong(new ByeongMoveStrategy(), new NormalMovementPolicy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(testPieces, TEST_PO.findMovablePath(start, destination)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 목적지와_포_사이에_기물이_없으면_예외가_발생해야_한다() {
        //given
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        HashMap<Position, Piece> emptyBoard = new HashMap<>();
        emptyBoard.put(start, TEST_PO);

        BoardStatus testBoard = BoardStatus.from(emptyBoard);

        //when, then
        Assertions.assertThatThrownBy(() -> TEST_PO.movePolicy(emptyBoard, TEST_PO.findMovablePath(start, destination)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
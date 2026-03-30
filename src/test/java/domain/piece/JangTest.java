package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.BoardStatus;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class JangTest {

    @Test
    void 목적지와_출발지_사이에_상대_기물이_존재할_수_없으므로_잘_간다고_판단한다() {
        //given
        Jang testPiece = new Jang(new SingleStepMoveStrategy(), new NormalMovementPolicy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when & then
        assertDoesNotThrow(() -> testPiece.check(testBoard, start, destination));
    }
}
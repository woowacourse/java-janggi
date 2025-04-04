package validator;

import domain.board.Board;
import domain.position.Position;

import java.util.List;

public interface ObstructionCheckable {

    default void validateObstruction(Board board, List<Position> internalPositions, int expectedCount) {
        if (!board.isCorrectExistPositionCount(internalPositions, expectedCount)) {
            throw new IllegalArgumentException("기물 경로에 장애물이 예상한 만큼이 아닙니다.");
        }
    }
}

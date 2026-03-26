package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import java.util.List;

public class EmptyCondition implements MoveCondition {

    @Override
    public void checkPath(List<Position> path, Camp camp, Board board, PieceRule pieceRule) {
        for (int i = 0; i < path.size() - 1; i++) {
            validateEmptyPosition(path.get(i), board);
        }
        validateGoalPosition(path.getLast(), camp, board);
    }

    private void validateEmptyPosition(Position position, Board board) {
        if (board.hasPieceAt(position)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private void validateGoalPosition(Position lastPosition, Camp camp, Board board) {
        if (board.isSameCampPieceAt(lastPosition, camp)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}

package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Camp;
import java.util.List;

public class EmptyCondition implements MoveCondition {

    @Override
    public void checkPath(List<Position> path, Camp camp, JanggiBoard board) {
        for (int i = 0; i < path.size() - 1; i++) {
            validateEmptyPosition(path, board, i);
        }
        validateGoalPosition(path.getLast(), camp, board);
    }

    private void validateEmptyPosition(List<Position> path, JanggiBoard board, int i) {
        if (board.getBoard().containsKey(path.get(i))) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private void validateGoalPosition(Position lastPosition, Camp camp, JanggiBoard board) {
        if (board.findPiece(lastPosition).isSameCamp(camp)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}

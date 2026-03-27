package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class EmptyCondition implements MoveCondition {

    @Override
    public void checkPath(List<Position> path, Camp camp, BoardChecker board, PieceRule pieceRule) {
        for (int i = 0; i < path.size() - 1; i++) {
            validateEmptyPosition(path.get(i), board);
        }
        validateDestination(path.getLast(), camp, board);
    }

    private void validateEmptyPosition(Position position, BoardChecker board) {
        if (board.hasPieceAt(position)) {
            throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }
    }

    private void validateDestination(Position destination, Camp camp, BoardChecker board) {
        if (board.isSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
        }
    }
}

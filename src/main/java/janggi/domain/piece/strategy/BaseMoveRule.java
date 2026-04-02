package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.List;

public abstract class BaseMoveRule implements MoveRule {

    @Override
    public abstract void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule);

    protected void validateEmptyPath(List<Position> path, BoardChecker board) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (board.hasPieceAt(path.get(i))) {
                throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
            }
        }
    }
}

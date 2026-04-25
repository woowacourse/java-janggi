package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import java.util.List;

public class ChaMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isStraight(from, to)) {
            return !isStraightPathBlocked(from, to, boardView);
        }

        if (isValidPalaceDiagonalMove(from, to)) {
            return !isDiagonalPathBlocked(from, to, boardView);
        }

        return false;
    }

    private boolean isStraight(Position from, Position to) {
        return from.isSameRow(to) || from.isSameColumn(to);
    }

    private boolean isStraightPathBlocked(Position from, Position to, BoardView boardView) {
        List<Position> path = from.getStraightPathTo(to);
        return path.stream().anyMatch(boardView::hasPieceAt);
    }

    private boolean isValidPalaceDiagonalMove(Position from, Position to) {
        if (!from.isInPalace() || !to.isInPalace()) {
            return false;
        }

        return from.isOnSameDiagonal(to);
    }

    private boolean isDiagonalPathBlocked(Position from, Position to, BoardView boardView) {
        if (from.calculateDistance(to) == 2) {
            Position middle = from.getMiddlePosition(to);
            return boardView.hasPieceAt(middle);
        }

        return false;
    }
}

package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Piece;
import janggi.domain.Position;
import java.util.List;

public class PoMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isStraight(from, to)) {
            return canMoveStraight(from, to, boardView);
        }

        if (isValidPalaceDiagonal(from, to)) {
            return canMoveDiagonal(from, to, boardView);
        }

        return false;
    }

    private boolean isStraight(Position from, Position to) {
        return from.getRowValue() == to.getRowValue() && from.getColumnValue() == to.getColumnValue();
    }

    private boolean canMoveStraight(Position from, Position to, BoardView boardView) {
        List<Piece> pathPieces = getPathPieces(from, to, boardView);

        if (isInvalidPath(pathPieces)) {
            return false;
        }

        return isValidTarget(to, boardView);
    }

    private boolean isValidPalaceDiagonal(Position from, Position to) {
        return from.isInPalace() && to.isInPalace() &&
                from.isOnSameDiagonal(to) && from.calculateDistance(to) == 2;
    }

    private boolean canMoveDiagonal(Position from, Position to, BoardView boardView) {
        Position middle = from.getMiddlePosition(to);

        if (!boardView.hasPieceAt(middle)) {
            return false;
        }

        Piece bridge = boardView.getPieceAt(middle);
        if (isPo(bridge)) {
            return false;
        }

        return isValidTarget(to, boardView);
    }

    private List<Piece> getPathPieces(Position from, Position to, BoardView boardView) {
        return from.getStraightPathTo(to).stream()
                .filter(boardView::hasPieceAt)
                .map(boardView::getPieceAt)
                .toList();
    }

    private boolean isInvalidPath(List<Piece> pathPieces) {
        if (pathPieces.size() != 1) {
            return true;
        }
        return isPo(pathPieces.get(0));
    }

    private boolean isValidTarget(Position to, BoardView boardView) {
        if (!boardView.hasPieceAt(to)) {
            return true;
        }

        return !isPo(boardView.getPieceAt(to));
    }

    private boolean isPo(Piece piece) {
        return piece.getName().equals("包");
    }
}

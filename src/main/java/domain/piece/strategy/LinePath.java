package domain.piece.strategy;

import domain.board.BoardState;
import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;
import domain.position.PositionCalculator;

public class LinePath {
    private final Directions directions;
    private Position currentPosition;

    public LinePath(Position startPosition, Directions directions) {
        this.currentPosition = startPosition;
        this.directions = directions;
    }

    public boolean hasNext() {
        return directions.hasNext();
    }

    public boolean moveForward() {
        Direction currentDirection = directions.next();
        if (!directions.keepsDirection(currentDirection)) {
            return false;
        }
        currentPosition = PositionCalculator.add(currentPosition, currentDirection);
        return true;
    }

    public boolean isAt(Position endPosition) {
        return currentPosition.equals(endPosition);
    }

    public boolean isBlockedBy(BoardState boardState) {
        return boardState.isBlocked(currentPosition);
    }

    public int size() {
        return directions.size();
    }

    public Piece findCurrentPiece(BoardState boardState) {
        return boardState.findBy(currentPosition);
    }
}

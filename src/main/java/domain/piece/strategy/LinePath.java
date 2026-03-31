package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;

import java.util.Map;

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
        currentPosition = currentPosition.append(currentDirection);
        return true;
    }

    public boolean isAt(Position endPosition) {
        return currentPosition.equals(endPosition);
    }

    public boolean isBlockedBy(Map<Position, Piece> state) {
        return state.containsKey(currentPosition);
    }

    public boolean isBlockedByCanon(Map<Position, Piece> state) {
        if (!isBlockedBy(state)) {
            return false;
        }
        return state.get(currentPosition).isSamePieceType(PieceType.CANON);
    }

    public int size() {
        return directions.size();
    }
}

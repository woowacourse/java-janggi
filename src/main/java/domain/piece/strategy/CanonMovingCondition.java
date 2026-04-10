package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;

import java.util.Map;
import java.util.Queue;

public class CanonMovingCondition implements MovingCondition {

    private static final int MAX_PIECE_COUNT_ON_THE_PATH = 1;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        if (!isInPalaceEdgePath(startPosition, endPosition) && !isStraightDirection(directions)) return false;
        return hasValidCanonPath(state, startPosition, endPosition, directions);
    }

    private boolean hasValidCanonPath(
            Map<Position, Piece> state,
            Position startPosition,
            Position endPosition,
            Queue<Direction> directions
    ) {
        Direction standartDirection = directions.peek();
        Position currentPosition = startPosition;
        int pieceCount = 0;

        while (!directions.isEmpty()) {
            Direction currentDirection = directions.poll();
            if (currentDirection != standartDirection) return false;
            currentPosition = currentPosition.append(currentDirection);
            if (isBlockingCanon(state, currentPosition)) return false;
            if (isCountablePiece(state, currentPosition, endPosition)) pieceCount++;
            if (pieceCount > MAX_PIECE_COUNT_ON_THE_PATH) return false;
        }
        return pieceCount == MAX_PIECE_COUNT_ON_THE_PATH;
    }

    private boolean isInPalaceEdgePath(Position startPosition, Position endPosition) {
        return startPosition.isPalaceEdge() && endPosition.isPalaceEdge();
    }

    private boolean isStraightDirection(Queue<Direction> directions) {
        return !directions.isEmpty() && directions.peek().isStraight();
    }

    private boolean isBlockingCanon(Map<Position, Piece> state, Position position) {
        return state.containsKey(position)
                && state.get(position).isSamePieceType(PieceType.CANON);
    }

    private boolean isCountablePiece(
            Map<Position, Piece> state,
            Position position,
            Position endPosition
    ) {
        return state.containsKey(position) && !position.equals(endPosition);
    }
}

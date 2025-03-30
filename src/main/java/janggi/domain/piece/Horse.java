package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Direction;
import janggi.domain.movement.Directions;
import janggi.domain.movement.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Horse extends Piece {

    private static final List<Directions> DIRECTIONS_LIST = List.of(
            Directions.of(Direction.DOWN, Direction.DOWN_DOWN_LEFT),
            Directions.of(Direction.DOWN, Direction.DOWN_DOWN_RIGHT)
    );

    public Horse(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        Set<Position> result = new HashSet<>();
        List<Directions> rotatedDirections = new ArrayList<>(DIRECTIONS_LIST);
        for (int i = 0; i < 4; i++) {
            searchAvailableMoves(result, pieces, currentPosition, rotatedDirections);
            rotatedDirections = Directions.rotate(rotatedDirections);
        }

        return result;
    }

    private void searchAvailableMoves(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                      List<Directions> directionsList) {
        for (Directions directions : directionsList) {
            searchAvailableMove(result, pieces, position, directions.directions());
        }
    }

    private void searchAvailableMove(Set<Position> result, Map<Position, Piece> pieces, Position position,
                                     List<Direction> directions) {
        if (canNotMove(directions, position)) {
            return;
        }

        Position midPosition = position.moveToNextPosition(directions.get(0).getVector());
        Position finalPosition = position.moveToNextPosition(directions.get(1).getVector());

        if (!pieces.containsKey(midPosition) && canMoveToPosition(pieces, finalPosition)) {
            result.add(finalPosition);
        }
    }

    private boolean canNotMove(List<Direction> directions, Position currentPosition) {
        return directions.stream()
                .map(Direction::getVector)
                .anyMatch(currentPosition::canNotMove);
    }

    private boolean canMoveToPosition(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return true;
        }
        Piece nextPiece = pieces.get(position);
        return !nextPiece.isSameSide(side);
    }

    @Override
    public double getPoints() {
        return 5;
    }
}

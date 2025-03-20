package domain.piece;

import domain.Direction;
import domain.Position;
import domain.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT);
    }


    public Cannon(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Cannon(Cannon cannon) {
        super(cannon);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        Direction direction = findDirectionToReachAt(expectedPosition);
        if (direction == null) {
            return false;
        }
        if (hasCannonPieceAtIntermediatePositions(expectedPosition, pieces, direction)) {
            return false;
        }
        if (hasOnlyOnePieceAtIntermediatePositions(expectedPosition, pieces, direction)) {
            return false;
        }
        return hasNotTeamAtPosition(expectedPosition, pieces, (piece -> piece.isSameType(PieceType.CANNON)));
    }

    private boolean hasCannonPieceAtIntermediatePositions(Position expectedPosition, List<Piece> pieces,
                                                          Direction direction) {
        List<Position> intermediatePositions = findIntermediatePositions(direction, this.position, expectedPosition);
        for (Position intermediatePosition : intermediatePositions) {
            for (Piece piece : pieces) {
                if (piece.hasSamePosition(intermediatePosition) && piece.isSameType(PieceType.CANNON)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasOnlyOnePieceAtIntermediatePositions(Position expectedPosition, List<Piece> pieces,
                                                           Direction direction) {
        List<Position> intermediatePositions = findIntermediatePositions(direction, this.position, expectedPosition);
        int count = 0;
        for (Position intermediatePosition : intermediatePositions) {
            for (Piece piece : pieces) {
                if (piece.hasSamePosition(intermediatePosition)) {
                    count++;
                }
            }
        }
        return count != 1;
    }

    private Direction findDirectionToReachAt(Position expectedPosition) {
        for (Direction nextDirection : directions) {
            Direction findDirection = findDirection(expectedPosition, nextDirection);
            if (findDirection != null) {
                return findDirection;
            }
        }
        return null;
    }

    private Direction findDirection(Position expectedPosition, Direction nextDirection) {
        Position current = this.position;
        while (current.canMovePosition(nextDirection.getDeltaRow(), nextDirection.getDeltaColumn())) {
            current = current.movePosition(nextDirection.getDeltaRow(), nextDirection.getDeltaColumn());
            if (current.equals(expectedPosition)) {
                return nextDirection;
            }
        }
        return null;
    }

    private List<Position> findIntermediatePositions(Direction direction, Position start, Position end) {
        Position cur = start;
        List<Position> positions = new ArrayList<>();
        while (!cur.equals(end)) {
            cur = cur.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
            positions.add(cur);
        }
        positions.removeLast();
        return positions;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Piece newInstance() {
        return new Cannon(this);
    }

}

package domain.piece;

import domain.Direction;
import domain.Position;
import domain.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN,Direction.UP,Direction.LEFT,Direction.RIGHT);
    }

    public Chariot(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Chariot(Chariot chariot){
        super(chariot);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        Direction direction = findDirectionToReachAt(expectedPosition);
        if (direction == null) {
            return false;
        }
        if (hasPieceAtIntermediatePositions(expectedPosition, pieces, direction)) {
            return false;
        }
        return hasNotTeamAtPosition(expectedPosition, pieces, (piece -> false));
    }

    private boolean hasPieceAtIntermediatePositions(Position expectedPosition, List<Piece> pieces,
                                                    Direction direction) {
        List<Position> intermediatePositions = findIntermediatePositions(direction, this.position, expectedPosition);
        return hasBlockedPiece(intermediatePositions, pieces);
    }

    private boolean hasBlockedPiece(List<Position> intermediatePositions, List<Piece> alivePieces) {
        return intermediatePositions.stream()
                .anyMatch(position -> hasPieceTo(position, alivePieces));
    }

    private boolean hasPieceTo(Position position, List<Piece> alivePieces) {
        return alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(position));
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
        return PieceType.CHARIOT;
    }

    @Override
    public Piece newInstance() {
        return new Chariot(this);
    }

}

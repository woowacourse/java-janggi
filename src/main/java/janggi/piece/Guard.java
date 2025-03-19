package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Guard implements Piece {

    private final Movement movement = Movement.GUARD;
    private final Position position;

    public Guard(Position position) {
        validatePositionRange(position);
        this.position = position;
    }

    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(Piece selectedPiece) {
        return null;
/*        return possiblePositions.stream()
                .collect(Collectors.toMap(
                        possiblePosition -> possiblePosition,  // 키: 해당 Position
                        possiblePosition -> isOneSpaceAway(this.position, possiblePosition)
                                || isAtThatSpace(this.position, possiblePosition) // 값: 한 칸 차이 여부
                ));*/
    }

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) {
        int dx = Math.abs(piecePosition.getX() - possiblePosition.getX());
        int dy = Math.abs(piecePosition.getY() - possiblePosition.getY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean canMoveFirstStep(Piece seletedPiece) {
        return false;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
        return movement.getDirections()
                .stream()
                .map(direction ->
                        {
                            Position position = direction.plusOffsetToPosition(this.position);
                            Optional<Position> optionalPosition = makePositionWithOptional(position);
                            return optionalPosition;
                        }
                )
                .flatMap(Optional::stream)
                .toList();
    }
}

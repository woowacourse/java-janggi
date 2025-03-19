package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SoldierHan implements Piece{

    private final Movement movement = Movement.SOLDIER_HAN;
    private final Position position;

    public SoldierHan(Position position) {
        validatePositionRange(position);
        this.position = position;
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
                            return makePositionWithOptional(position);
                        }
                )
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(Piece selectedPiece) {
        return Map.of();
    }

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) {
        return false;
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
    public boolean isAtThatSpace(Position piecePosition, Position possiblePosition) {
        return Piece.super.isAtThatSpace(piecePosition, possiblePosition);
    }
}

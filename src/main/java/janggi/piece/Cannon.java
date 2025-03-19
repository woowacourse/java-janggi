package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Cannon implements Piece{

    private final Movement movement = Movement.CANNON;
    private final Position position;

    public Cannon(Position position) {
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
                            List<Position> positions = direction.testPlusOffset(position);
                            List<Optional<Position>> optionalPositions = new ArrayList<>();
                            for (Position position : positions) {
                                Optional<Position> optionalPosition = makePositionWithOptional(position);
                                optionalPositions.add(optionalPosition);
                            }
                            return optionalPositions;
                        }
                )
                .flatMap(List::stream)
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(List<Position> possiblePositions) {
        return Map.of();
    }

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) {
        return false;
    }

    @Override
    public boolean isAtThatSpace(Position piecePosition, Position possiblePosition) {
        return Piece.super.isAtThatSpace(piecePosition, possiblePosition);
    }
}

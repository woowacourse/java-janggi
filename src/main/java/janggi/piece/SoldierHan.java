package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Optional;

public class SoldierHan implements Piece{

    private final Movement movement = Movement.SOLDIER_HAN;
    private final Position position;

    public SoldierHan(Position position) {
        this.position = position;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
        return movement.getDirections()
                .stream()
                .map(direction -> checkOutOfBoundsPosition(direction.plusOffsetToPosition(position)))
                .flatMap(Optional::stream)
                .toList();
    }
}

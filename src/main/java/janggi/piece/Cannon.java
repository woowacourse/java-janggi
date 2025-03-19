package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Optional;

public class Cannon implements Piece{

    private final Movement movement = Movement.CANNON;
    private final Position position;

    public Cannon(Position position) {
        this.position = position;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
        return null;
    }
}

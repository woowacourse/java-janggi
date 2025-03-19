package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Chariot implements Piece{

    private final Movement movement = Movement.CHARIOT;
    private final Position position;

    public Chariot(Position position) {
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

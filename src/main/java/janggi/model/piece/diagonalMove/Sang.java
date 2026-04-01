package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.position.Position;
import janggi.model.movement.Movement;
import janggi.model.movement.SangMovement;

public class Sang extends DiagonalMovePiece {

    private final Movement movement;

    public Sang(Team team) {
        super(team);
        this.movement = new SangMovement();
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}

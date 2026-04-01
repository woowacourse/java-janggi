package janggi.model.piece.diagonalMove;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.position.Position;
import janggi.model.movement.MaMovement;
import janggi.model.movement.Movement;

public class Ma extends DiagonalMovePiece {

    private final Movement movement;


    public Ma(Team team) {
        super(team);
        this.movement = new MaMovement();
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}

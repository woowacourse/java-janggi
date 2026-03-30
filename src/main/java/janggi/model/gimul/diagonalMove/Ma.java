package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;
import janggi.model.board.movement.MaMovement;
import janggi.model.board.movement.Movement;

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

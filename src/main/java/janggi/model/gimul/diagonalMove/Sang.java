package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;
import janggi.model.board.movement.Movement;
import janggi.model.board.movement.SangMovement;

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

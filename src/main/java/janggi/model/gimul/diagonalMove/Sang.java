package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.movement.Movement;
import janggi.model.board.movement.SangMovement;

public class Sang extends AbstractDiagonalGimul {

    private final Movement movement;

    public Sang(Team team) {
        super(team);
        this.movement = new SangMovement();
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}

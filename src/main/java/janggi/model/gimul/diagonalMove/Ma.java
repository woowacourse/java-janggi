package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.movement.MaMovement;
import janggi.model.board.movement.Movement;

public class Ma extends AbstractDiagonalGimul {

    private final Movement movement;


    public Ma(Team team) {
        super(team);
        this.movement = new MaMovement();
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }
}

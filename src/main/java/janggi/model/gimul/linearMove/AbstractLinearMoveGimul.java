package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.gimul.AbstractGimul;
import janggi.model.board.position.Position;
import janggi.model.board.movement.Movement;
import janggi.model.board.movement.StraightMovement;
import java.util.List;

public abstract class AbstractLinearMoveGimul extends AbstractGimul {

    protected final Movement movement;

    protected AbstractLinearMoveGimul(Team team) {
        super(team);
        this.movement = new StraightMovement();
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return new StraightMovement().move(from, to);
    }


    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }
}

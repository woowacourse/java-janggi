package janggi.model.gimul.palace;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.movement.Movement;
import janggi.model.board.movement.OneStepMovement;
import java.util.List;

public abstract class AbstractPalaceGimul extends AbstractGimul {

    private final Movement movement;

    protected AbstractPalaceGimul(Team team) {
        super(team);
        movement = new OneStepMovement();
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }

}

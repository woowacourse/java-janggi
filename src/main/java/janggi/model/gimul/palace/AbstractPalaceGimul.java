package janggi.model.gimul.palace;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;
import janggi.model.position.PositionPath;
import java.util.List;

public abstract class AbstractPalaceGimul extends AbstractGimul {

    public AbstractPalaceGimul(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);
        if (positionDelta.isMultiStep()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        return calculatePath(from, positionDelta);
    }

    private PositionPath calculatePath(Position from, PositionDelta positionDelta) {
        if (positionDelta.isHorizontal()) {
            return from.moveHorizontal(positionDelta.columnDistance()).getMiddlePath();
        }
        if (positionDelta.isVertical()) {
            return from.moveVertical(positionDelta.rowDistance()).getMiddlePath();
        }
        return from.moveDiagonal(new DiagonalDelta(
                positionDelta.rowDistance(),
                positionDelta.columnDistance()
        )).getMiddlePath();
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

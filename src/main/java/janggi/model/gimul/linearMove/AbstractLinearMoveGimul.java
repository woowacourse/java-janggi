package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public abstract class AbstractLinearMoveGimul extends AbstractGimul {

    protected AbstractLinearMoveGimul(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (from.equals(to)
                || isDifferentRowAndColumn(from, to)
        ) {
            throw new IllegalArgumentException("해당 경로로는 이동할 수 없습니다.");
        }

        if (from.isSameRow(to)) {
            return from.moveHorizontal(to.getColumnDistance(from)).removeFromAndTo();
        }

        return from.moveVertical(to.getRowDistance(from)).removeFromAndTo();
    }

    private boolean isDifferentRowAndColumn(Position from, Position to) {
        return !from.isSameRow(to) && !from.isSameColumn(to);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }
}

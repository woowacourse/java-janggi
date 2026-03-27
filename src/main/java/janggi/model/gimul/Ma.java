package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;
import janggi.model.position.PositionPath;
import java.util.List;

public class Ma extends AbstractGimul {

    public Ma(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);

        if (positionDelta.isMoreThanOneStepAndDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        int firstDistance = positionDelta.getUnitDistance();
        PositionPath first = from.moveVertical(firstDistance);
        PositionDelta moved = positionDelta.movedVertically(firstDistance);

        if (positionDelta.isHorizontalLongerThanVertical()) {
            first = from.moveHorizontal(firstDistance);
            moved = positionDelta.movedHorizontally(firstDistance);
        }

        PositionPath second = first.getDestination().moveDiagonal(
                new DiagonalDelta(moved.rowDistance(), moved.columnDistance())
        );

        return PositionPath.concatenate(first, second).removeFromAndTo();
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }

    @Override
    public String getSymbol() {
        return "마";
    }
}

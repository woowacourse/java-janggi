package janggi.gimul;

import janggi.Team;
import janggi.position.DiagonalDelta;
import janggi.position.Position;
import janggi.position.PositionDelta;
import janggi.position.PositionPath;
import java.util.List;

public class Sang extends Gimul {

    public Sang(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);

        if (positionDelta.isMoreThanOneStepAndDoubleDiagonal()) {
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
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.isEmpty() && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }
}

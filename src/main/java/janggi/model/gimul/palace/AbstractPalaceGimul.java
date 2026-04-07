package janggi.model.gimul.palace;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.GimulType;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;
import janggi.model.position.PositionPath;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPalaceGimul extends AbstractGimul {


    public AbstractPalaceGimul(Team team, GimulType type) {
        super(team, type);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        validateInPalace(from, to);
        PositionDelta positionDelta = PositionDelta.between(from, to);
        if (positionDelta.isMultiStep()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        if (!positionDelta.isHorizontal() && !positionDelta.isVertical()) {
            validatePalaceDiagonal(from, to);
        }
        return calculatePath(from, positionDelta);
    }

    private void validateInPalace(Position from, Position to) {
        if (!from.isInPalace() || !to.isInPalace()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private void validatePalaceDiagonal(Position from, Position to) {
        if (!from.isOnPalaceDiagonal() || !to.isOnPalaceDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
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
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo) {
        return gimulsOnPath.isEmpty()
                && gimulAtTo.map(gimul -> !this.isSameTeam(gimul)).orElse(true);
    }
}

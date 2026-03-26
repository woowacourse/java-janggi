package janggi.gimul;

import janggi.Team;
import janggi.position.DiagonalDelta;
import janggi.position.Position;
import janggi.position.PositionDelta;
import janggi.position.PositionPath;
import java.util.List;

public class Jang extends Gimul {
    public Jang(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);

        if (positionDelta.isMoreThanOneStepIncludingDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (positionDelta.isHorizontal()) {
            return from.moveHorizontal(positionDelta.columnDistance());
        }

        if (positionDelta.isVertical()) {
            return from.moveVertical(positionDelta.rowDistance());
        }

        DiagonalDelta diagonalDelta = new DiagonalDelta(
                positionDelta.rowDistance(),
                positionDelta.columnDistance()
        );

        return from.moveDiagonal(diagonalDelta).removeFromAndTo();
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.isEmpty() && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }
}

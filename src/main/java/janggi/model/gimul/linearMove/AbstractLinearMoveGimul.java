package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.GimulType;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;

public abstract class AbstractLinearMoveGimul extends AbstractGimul {

    public AbstractLinearMoveGimul(Team team, GimulType type) {
        super(team, type);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (isPalaceMove(from, to)) {
            return createPalacePath(from, to);
        }
        return createLinearPath(from, to);
    }

    private boolean isPalaceMove(Position from, Position to) {
        return from.isOnPalaceDiagonal() && to.isOnPalaceDiagonal() && isDiagonal(from, to);
    }

    private boolean isDiagonal(Position from, Position to) {
        int rowDistance = Math.abs(to.getRowDistance(from));
        int columnDistance = Math.abs(to.getColumnDistance(from));
        return rowDistance == columnDistance && rowDistance != 0;
    }

    private PositionPath createPalacePath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);
        return from.moveDiagonal(new DiagonalDelta(rowDistance, columnDistance)).getMiddlePath();
    }

    private PositionPath createLinearPath(Position from, Position to) {
        validateLinearMove(from, to);

        if (from.isSameRow(to)) {
            return from.moveHorizontal(to.getColumnDistance(from)).getMiddlePath();
        }

        return from.moveVertical(to.getRowDistance(from)).getMiddlePath();
    }

    private void validateLinearMove(Position from, Position to) {
        if ((!from.isSameRow(to) && !from.isSameColumn(to)) || from.equals(to)) {
            throw new IllegalArgumentException("해당 경로로는 이동할 수 없습니다.");
        }
    }
}

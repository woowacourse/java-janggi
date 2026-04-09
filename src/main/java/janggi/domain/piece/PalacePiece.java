package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

public abstract class PalacePiece extends MoveablePiece {

    protected final Palace palace;

    public PalacePiece(Team team) {
        super(team);
        this.palace = Palace.getInstance();
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        return findPath(movement);
    }

    private void validateMove(Movement movement) {
        if (!(isNormalMove(movement) || isPalaceMove(movement))) {
            throw new IllegalArgumentException(
                    "[ERROR] " + getType() + "은(는) 해당 위치로 이동할 수 없습니다.");
        }
    }

    protected boolean isNormalMove(Movement movement) {
        return isStraightMove(movement);
    }

    protected boolean isPalaceMove(Movement movement) {
        return palace.hasRoute(movement.getFrom(), movement.getTo());
    }

    private boolean isStraightMove(Movement movement) {
        return movement.calculateRowDiff() == 0 || movement.calculateColumnDiff() == 0;
    }

    private Path findPath(Movement movement) {
        if (isDiagonalMove(movement)) {
            return findDiagonalPath(movement);
        }
        return findStraightPath(movement);
    }

    private boolean isDiagonalMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());
        return absRowDiff == absColumnDiff;
    }

    private Path findDiagonalPath(Movement movement) {
        Path path = new Path();
        Position to = movement.getTo();
        Position target = movement.getFrom().nextDiagonal(to);
        while (!target.equals(to)) {
            path.add(target);
            target = target.nextDiagonal(to);
        }
        return path;
    }

    private Path findStraightPath(Movement movement) {
        Path path = new Path();
        Position to = movement.getTo();
        Position target = movement.getFrom().nextStraight(to);
        while (!target.equals(to)) {
            path.add(target);
            target = target.nextStraight(to);
        }
        return path;
    }
}

package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

public abstract class PalacePiece extends MoveablePiece {

    protected final Palace palace;

    public PalacePiece(Team team, Palace palace) {
        super(team);
        this.palace = palace;
    }

    protected Path findPath(Movement movement) {
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

package janggi.domain.board;

import janggi.domain.piece.Team;

public class Palace {
    private static final int MIN_X = 4;
    private static final int MAX_X = 6;
    private static final int HAN_MIN_Y = 1;
    private static final int HAN_MAX_Y = 3;
    private static final int CHO_MIN_Y = 8;
    private static final int CHO_MAX_Y = 10;
    private static final Position HAN_CENTER = new Position(5, 2);
    private static final Position CHO_CENTER = new Position(5, 9);

    public boolean canMove(Team team, Position from, Position to) {
        if (contains(team, from) && contains(team, to)) {
            if (canMoveStraightOneStep(from, to)) {
                return true;
            }
            return canMoveDiagonally(team, from, to);
        }
        return false;
    }

    private boolean contains(Team team, Position position) {
        if (position.x() < MIN_X || position.x() > MAX_X) {
            return false;
        }
        if (team == Team.HAN) {
            return HAN_MIN_Y <= position.y() && position.y() <= HAN_MAX_Y;
        }
        return CHO_MIN_Y <= position.y() && position.y() <= CHO_MAX_Y;
    }

    private boolean canMoveStraightOneStep(Position from, Position to) {
        return from.distanceX(to) + from.distanceY(to) == 1;
    }

    private boolean canMoveDiagonally(Team team, Position from, Position to) {
        if (from.distanceX(to) != 1 || from.distanceY(to) != 1) {
            return false;
        }
        Position center = findCenter(team);
        return (from.equals(center) && isCorner(team, to))
                || (to.equals(center) && isCorner(team, from));
    }

    private Position findCenter(Team team) {
        if (team == Team.HAN) {
            return HAN_CENTER;
        }
        return CHO_CENTER;
    }

    private boolean isCorner(Team team, Position position) {
        Position center = findCenter(team);
        return center.distanceX(position) == 1 && center.distanceY(position) == 1;
    }
}

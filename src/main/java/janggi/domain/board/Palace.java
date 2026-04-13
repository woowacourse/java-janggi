package janggi.domain.board;

import janggi.domain.piece.Team;
import java.util.List;

public class Palace {
    private static final int MIN_X = 4;
    private static final int MAX_X = 6;
    private static final int HAN_MIN_Y = 1;
    private static final int HAN_MAX_Y = 3;
    private static final int CHO_MIN_Y = 8;
    private static final int CHO_MAX_Y = 10;
    private static final Position HAN_CENTER = new Position(5, 2);
    private static final Position CHO_CENTER = new Position(5, 9);

    public boolean canMoveOnDiagonalLine(Position from, Position to) {
        if (!isInsideSamePalace(from, to)) {
            return false;
        }
        Team palaceTeam = findPalaceTeam(from);
        if (canMoveOneStepDiagonally(palaceTeam, from, to)) {
            return true;
        }
        return isOppositeCornerMove(palaceTeam, from, to);
    }

    public List<Position> findDiagonalPath(Position from, Position to) {
        if (!canMoveOnDiagonalLine(from, to)) {
            throw new IllegalArgumentException("궁성 대각선 경로를 생성할 수 없습니다.");
        }
        Position center = findCenter(findPalaceTeam(from));
        if (from.equals(center) || to.equals(center)) {
            return List.of(to);
        }
        return List.of(center, to);
    }

    public boolean contains(Team team, Position position) {
        if (position.x() < MIN_X || position.x() > MAX_X) {
            return false;
        }
        if (team == Team.HAN) {
            return HAN_MIN_Y <= position.y() && position.y() <= HAN_MAX_Y;
        }
        return CHO_MIN_Y <= position.y() && position.y() <= CHO_MAX_Y;
    }

    public boolean canMoveOneStepDiagonally(Team team, Position from, Position to) {
        if (from.distanceX(to) != 1 || from.distanceY(to) != 1) {
            return false;
        }
        Position center = findCenter(team);
        return (from.equals(center) && isCorner(team, to))
                || (to.equals(center) && isCorner(team, from));
    }

    private boolean isInsideSamePalace(Position from, Position to) {
        return (contains(Team.HAN, from) && contains(Team.HAN, to))
                || (contains(Team.CHO, from) && contains(Team.CHO, to));
    }

    private Team findPalaceTeam(Position position) {
        if (contains(Team.HAN, position)) {
            return Team.HAN;
        }
        return Team.CHO;
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

    private boolean isOppositeCornerMove(Team team, Position from, Position to) {
        return isCorner(team, from)
                && isCorner(team, to)
                && from.distanceX(to) == 2
                && from.distanceY(to) == 2;
    }
}

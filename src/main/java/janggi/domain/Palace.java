package janggi.domain;

import janggi.domain.movepath.DirectionalMovePath;
import janggi.domain.movepath.FixedMovePath;
import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Optional;

public class Palace {

    private static final int MIN_X = 4;
    private static final int MAX_X = 6;
    private static final int CHU_MIN_Y = 1;
    private static final int CHU_MAX_Y = 3;
    private static final int HAN_MIN_Y = 8;
    private static final int HAN_MAX_Y = 10;
    private static final int CHU_CENTER_Y = 2;
    private static final int HAN_CENTER_Y = 9;
    private static final int CENTER_X = 5;

    public boolean isInside(Position position) {
        return isInside(position, TeamType.CHU) || isInside(position, TeamType.HAN);
    }

    public boolean isInside(Position position, TeamType teamType) {
        if (teamType == TeamType.CHU) {
            return isInsideRange(position, CHU_MIN_Y, CHU_MAX_Y);
        }
        return isInsideRange(position, HAN_MIN_Y, HAN_MAX_Y);
    }

    public Optional<MovePathStrategy> findOneStepMovePath(Position start, Position end) {
        if (!isInside(start) || !isInside(end) || !isSamePalace(start, end)) {
            return Optional.empty();
        }

        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (isStraightOneStep(dx, dy)) {
            return Optional.of(new FixedMovePath(List.of(Delta.from(dx, dy))));
        }
        if (isConnectedDiagonal(start, end)) {
            return Optional.of(new FixedMovePath(List.of(Delta.from(dx, dy))));
        }
        return Optional.empty();
    }

    public Optional<MovePathStrategy> findDiagonalMovePath(Position start, Position end) {
        if (!isSamePalace(start, end) || !isDiagonalNode(start) || !isDiagonalNode(end)) {
            return Optional.empty();
        }
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (dx == 0 || Math.abs(dx) != Math.abs(dy) || Math.abs(dx) > 2) {
            return Optional.empty();
        }
        return Optional.of(
            new DirectionalMovePath(List.of(Delta.from(Integer.signum(dx), Integer.signum(dy))))
        );
    }

    public Optional<MovePathStrategy> findForwardDiagonalStepPath(
        Position start,
        Position end,
        TeamType teamType
    ) {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        if (Math.abs(dx) != 1 || Math.abs(dy) != 1) {
            return Optional.empty();
        }
        if (!isForward(teamType, dy)) {
            return Optional.empty();
        }
        return findOneStepMovePath(start, end)
            .filter(path -> isOneStepDiagonal(dx, dy));
    }

    private boolean isConnectedDiagonal(Position start, Position end) {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        return isOneStepDiagonal(dx, dy)
            && isDiagonalNode(start)
            && isDiagonalNode(end)
            && (isCenter(start) || isCenter(end));
    }

    private boolean isOneStepDiagonal(int dx, int dy) {
        return Math.abs(dx) == 1 && Math.abs(dy) == 1;
    }

    private boolean isSamePalace(Position start, Position end) {
        return isInside(start, TeamType.CHU) && isInside(end, TeamType.CHU)
            || isInside(start, TeamType.HAN) && isInside(end, TeamType.HAN);
    }

    private boolean isDiagonalNode(Position position) {
        return isCenter(position) || isCorner(position);
    }

    private boolean isInsideRange(Position position, int minY, int maxY) {
        return MIN_X <= position.getX() && position.getX() <= MAX_X
            && minY <= position.getY() && position.getY() <= maxY;
    }

    private boolean isCenter(Position position) {
        return position.getX() == CENTER_X
            && (position.getY() == CHU_CENTER_Y || position.getY() == HAN_CENTER_Y);
    }

    private boolean isCorner(Position position) {
        return (position.getX() == MIN_X || position.getX() == MAX_X)
            && (position.getY() == CHU_MIN_Y || position.getY() == CHU_MAX_Y
            || position.getY() == HAN_MIN_Y || position.getY() == HAN_MAX_Y);
    }

    private boolean isStraightOneStep(int dx, int dy) {
        return Math.abs(dx) + Math.abs(dy) == 1;
    }

    private boolean isForward(TeamType teamType, int dy) {
        if (teamType == TeamType.CHU) {
            return dy == 1;
        }
        return dy == -1;
    }
}

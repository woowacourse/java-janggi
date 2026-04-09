package janggi.domain.piece;

import janggi.domain.Palace;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    private static final int MIN_STEP = 1;
    private static final int NO_MOVE = 0;
    private static final int MIN_DIAGONAL_STEP = 1;
    private static final int MAX_DIAGONAL_STEP = 2;

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isValidMovePattern(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 차가 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        if (dy == NO_MOVE) {
            List<Position> positions = getHorizontalPath(from, dx);
            return new Path(positions);
        }
        if (dx == NO_MOVE) {
            List<Position> positions = getVerticalPath(from, dy);
            return new Path(positions);
        }
        List<Position> positions = getDiagonalPath(from, to);
        return new Path(positions);
    }

    private List<Position> getHorizontalPath(Position from, int dx) {
        List<Position> positions = new ArrayList<>();
        for (int i = MIN_STEP; i < Math.abs(dx); i++) {
            positions.add(new Position(from.x() + i, from.y()));
        }
        return positions;
    }

    private List<Position> getVerticalPath(Position from, int dy) {
        List<Position> positions = new ArrayList<>();
        for (int i = MIN_STEP; i < Math.abs(dy); i++) {
            positions.add(new Position(from.x(), from.y() + i));
        }
        return positions;
    }

    private List<Position> getDiagonalPath(Position from, Position to) {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position((from.x() + to.x()) / 2, (from.y() + to.y()) / 2));
        return positions;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        if (validBasicMovement(dx, dy)) {
            return true;
        }
        if (validDiagonalMovement(dx, dy)) {
            return isValidBoundary(from, to) && isValidPalaceMovePath(from, to);
        }
        return false;
    }

    private boolean validBasicMovement(int dx, int dy) {
        if ((Math.abs(dx) == NO_MOVE && Math.abs(dy) > NO_MOVE) ||
            (Math.abs(dx) > NO_MOVE && Math.abs(dy) == NO_MOVE)) {
            return true;
        }
        return false;
    }

    private boolean validDiagonalMovement(int dx, int dy) {
        if ((Math.abs(dx) == Math.abs(dy))) {
            return true;
        }
        return false;
    }

    private boolean isValidBoundary(Position from, Position to) {
        return Palace.onPalace(from) && Palace.onPalace(to);
    }

    private boolean isValidPalaceMovePath(Position from, Position to) {
        int dx = Math.abs(from.deltaX(to));
        int dy = Math.abs(from.deltaY(to));

        if (dx == MIN_DIAGONAL_STEP && dy == MIN_DIAGONAL_STEP) {
            return Palace.isPalaceCenter(from) || Palace.isPalaceCenter(to);
        }
        if (dx == MAX_DIAGONAL_STEP && dy == MAX_DIAGONAL_STEP) {
            return Palace.isPalaceCenter(getDiagonalPath(from, to).getFirst());
        }
        return false;
    }
}

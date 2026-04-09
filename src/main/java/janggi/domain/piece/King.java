package janggi.domain.piece;

import janggi.domain.Palace;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class King extends Piece {

    private static final int STEP = 1;
    private static final int NO_MOVE = 0;

    public King(Team team) {
        super(team, PieceType.KING);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isValidBoundary(from, to)) {
            throw new IllegalArgumentException("[ERROR] 궁은 궁성 내부에서만 이동할 수 있습니다.");
        }
        if (!isValidMovePattern(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 궁이 이동할 수 없습니다.");
        }
    }

    private boolean isValidBoundary(Position from, Position to) {
        return Palace.onPalace(from) && Palace.onPalace(to);
    }

    @Override
    public Path getPath(Position from, Position to) {
        return Path.EMPTY;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        if (validBasicMovement(dx, dy)) {
            return true;
        }
        if (validDiagonalMovement(dx, dy)) {
            return Palace.isPalaceCenter(from) || Palace.isPalaceCenter(to);
        }
        return false;
    }

    private boolean validBasicMovement(int dx, int dy) {
        if ((Math.abs(dx) == NO_MOVE && Math.abs(dy) == STEP) ||
            (Math.abs(dx) == STEP && Math.abs(dy) == NO_MOVE)) {
            return true;
        }
        return false;
    }

    private static boolean validDiagonalMovement(int dx, int dy) {
        if ((Math.abs(dx) == STEP && Math.abs(dy) == STEP)) {
            return true;
        }
        return false;
    }
}

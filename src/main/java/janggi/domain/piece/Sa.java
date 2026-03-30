package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Sa extends Piece {

    private static final int STEP = 1;
    private static final int NO_MOVE = 0;

    public Sa(Team team) {
        super(team, PieceType.SA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isValidMovePattern(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 사가 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        return Path.EMPTY;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == NO_MOVE && Math.abs(dy) == STEP) ||
            (Math.abs(dx) == STEP && Math.abs(dy) == NO_MOVE);
    }
}

package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Sa extends Piece {

    public Sa(Team team) {
        super(team, PieceType.SA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isValidMovePattern(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 사가 이동할 수 없습니다.");
    }

    @Override
    public Path getPath(Position from, Position to) {
        return Path.EMPTY;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == 0 && Math.abs(dy) == 1) ||
                (Math.abs(dx) == 1 && Math.abs(dy) == 0);
    }
}

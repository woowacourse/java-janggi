package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (moveStrategy(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 마가 이동할 수 없습니다.");
    }

    @Override
    public Path getPath(Position from, Position to) {
        return null;
    }

    private boolean moveStrategy(Position from, Position to) {
        int dx = from.diffX(to);
        int dy = from.diffY(to);

        return (Math.abs(dx) == 1 && Math.abs(dy) == 2) ||
                (Math.abs(dx) == 2 && Math.abs(dy) == 1);
    }
}

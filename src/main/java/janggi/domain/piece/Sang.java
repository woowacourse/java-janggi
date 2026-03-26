package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Sang extends Piece {

    public Sang(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (moveStrategy(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 상이 이동할 수 없습니다.");
    }

    @Override
    public Path getPath(Position from, Position to) {
        return null;
    }

    private boolean moveStrategy(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return (Math.abs(dx) == 3 && Math.abs(dy) == 2) ||
                (Math.abs(dx) == 2 && Math.abs(dy) == 3);
    }
}

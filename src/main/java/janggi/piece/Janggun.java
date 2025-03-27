package janggi.piece;

import janggi.position.Position;
import java.util.List;

public class Janggun extends Piece {

    public Janggun(final Team team, final Position position) {
        super(new PieceProfile(PieceType.JANGGUN, team), position);
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of();
    }

    @Override
    public boolean isMove(final Position position) {
        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();

        if (Math.abs(dx) == 1 && dy == 0 || Math.abs(dy) == 1 && dx == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }

}

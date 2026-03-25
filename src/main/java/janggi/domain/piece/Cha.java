package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (moveStrategy(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 차가 이동할 수 없습니다.");
    }

    private boolean moveStrategy(Position from, Position to) {
        return (Math.abs(from.getX() - to.getX()) == 0 && Math.abs(from.getY() - to.getY()) > 0) ||
            (Math.abs(from.getX() - to.getX()) > 0 && Math.abs(from.getY() - to.getY()) == 0);
    }
}

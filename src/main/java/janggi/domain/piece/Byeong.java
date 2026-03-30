package janggi.domain.piece;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (isMovable(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Override
    public Path getPath(Position from, Position to) {
        return Path.EMPTY;
    }

    private boolean isMovable(Position from, Position to) {
        return xMoveStrategy(from, to) || HanYMoveStrategy(from, to) || ChoYMoveStrategy(from, to);
    }

    private boolean xMoveStrategy(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return Math.abs(dx) == 1 && Math.abs(dy) == 0;
    }

    private boolean HanYMoveStrategy(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return Math.abs(dx) == 0 &&
            dy == -1 &&
            this.isEqualTeam(Team.HAN);
    }

    private boolean ChoYMoveStrategy(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        return Math.abs(dx) == 0 &&
            dy == +1 &&
            this.isEqualTeam(Team.CHO);
    }
}

package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Team;
import java.util.List;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (moveStrategy(from, to)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 병이 이동할 수 없습니다.");
    }

    @Override
    public List<Position> getRoutes(Position from, Position to) {
        return List.of();
    }

    private boolean moveStrategy(Position from, Position to) {
        return xMoveStrategy(from, to) || HanYMoveStrategy(from, to) || ChoYMoveStrategy(from, to);
    }

    private boolean xMoveStrategy(Position from, Position to) {
        int dx = from.diffX(to);
        int dy = from.diffY(to);

        return Math.abs(dx) == 1 && Math.abs(dy) == 0;
    }

    private boolean HanYMoveStrategy(Position from, Position to) {
        int dx = from.diffX(to);
        int dy = from.diffY(to);

        return Math.abs(dx) == 0 &&
                dy == 1 &&
                this.isEqualTeam(Team.HAN);
    }

    private boolean ChoYMoveStrategy(Position from, Position to) {
        int dx = from.diffX(to);
        int dy = from.diffY(to);

        return Math.abs(dx) == 0 &&
                dy == -1 &&
                this.isEqualTeam(Team.CHO);
    }
}

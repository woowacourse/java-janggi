package janggi.domain.piece;

import janggi.domain.Palace;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Team;

public class Byeong extends Piece {

    private static final int STEP = 1;
    private static final int HAN_STEP = -1 * STEP;
    private static final int CHO_STEP = STEP;
    private static final int NO_MOVE = 0;

    public Byeong(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public void validateMove(Position from, Position to) {
        if (!isValidMovePattern(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 병이 이동할 수 없습니다.");
        }
    }

    @Override
    public Path getPath(Position from, Position to) {
        return Path.EMPTY;
    }

    private boolean isValidMovePattern(Position from, Position to) {
        int dx = from.deltaX(to);
        int dy = from.deltaY(to);

        // 병 기본 움직임 검증
        if (xMoveStrategy(dx, dy) || HanYMoveStrategy(dx, dy) || ChoYMoveStrategy(dx, dy)) {
            return true;
        }
        // 병 궁성 내 대각선 움직임 검증
        if ((this.isEqualTeam(Team.HAN) && Math.abs(dx) == STEP && dy == HAN_STEP) ||
            (this.isEqualTeam(Team.CHO) && Math.abs(dx) == STEP && dy == CHO_STEP)) {
            return Palace.isPalaceCenter(from) || Palace.isPalaceCenter(to);
        }
        return false;
    }

    private boolean xMoveStrategy(int dx, int dy) {
        return Math.abs(dx) == STEP && Math.abs(dy) == NO_MOVE;
    }

    private boolean HanYMoveStrategy(int dx, int dy) {
        return Math.abs(dx) == NO_MOVE && dy == HAN_STEP && this.isEqualTeam(Team.HAN);
    }

    private boolean ChoYMoveStrategy(int dx, int dy) {
        return Math.abs(dx) == NO_MOVE && dy == CHO_STEP && this.isEqualTeam(Team.CHO);
    }
}

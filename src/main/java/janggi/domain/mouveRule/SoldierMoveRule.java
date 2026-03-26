package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

public final class SoldierMoveRule implements MoveRule {

    private final Team team;

    public SoldierMoveRule(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        int rowDis = to.getRow() - from.getRow();
        int colDis = to.getCol() - from.getCol();
        return isForward(rowDis, colDis) || isSideStep(rowDis, colDis);
    }

    // 한이 위쪽배치임 -> 행증가가 전진
    // 초가아래 배치 ->   행 감소가 전진
    private boolean isForward(int rowDis, int colDis) {
        int forwardDirection = (team == Team.CHO) ? -1 : 1;
        return rowDis == forwardDirection && colDis == 0;
    }

    private boolean isSideStep(int rowDis, int colDis) {
        return rowDis == 0 && Math.abs(colDis) == 1;
    }

}

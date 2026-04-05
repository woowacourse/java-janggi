package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class FinishTurn extends BaseTurn {
    private static final String INVALID_MOVE = "게임 종료 상태에서는 이동할 수 없습니다.";

    private final Side winnerSide;

    public FinishTurn(Board board, Side winnerSide, int turn) {
        super(board, turn);
        this.winnerSide = winnerSide;
    }

    @Override
    public TurnState move(Position start, Position end) {
        throw new IllegalStateException(INVALID_MOVE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Side getWinnerSide() {
        if(winnerSide.equals(Side.EMPTY)) {
            return board.getHighestScoreSide();
        }
        return winnerSide;
    }

    @Override
    public Side getCurrentSide() {
        return Side.EMPTY;
    }
}

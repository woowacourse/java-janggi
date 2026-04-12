package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

public class End implements GameState {

    private final Side winner;

    public End(Side winner) {
        this.winner = winner;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public void update(GameContext context, Piece piece) {
        throw new IllegalStateException("게임이 종료된 경우 상태를 업데이트 할 수 없습니다.");
    }

    @Override
    public Side getCurrentSide() {
        throw new IllegalStateException("게임이 종료된 경우 현재 진행중인 팀이 존재하지 않습니다.");
    }

    @Override
    public Side getWinner() {
        return winner;
    }
}

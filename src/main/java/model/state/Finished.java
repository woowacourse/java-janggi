package model.state;

import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class Finished implements JanggiState {

    private final Team winner;
    private final GameStatus status;

    public Finished(Team winner, GameStatus status) {
        this.winner = winner;
        this.status = status;
    }

    @Override
    public JanggiState next(Board board) {
        throw new IllegalStateException("종료된 게임은 더이상 진행할 수 없습니다.");
    }

    @Override
    public Team resolveWinner(Board board) {
        return winner;
    }

    @Override
    public boolean canPlaying() {
        return false;
    }

    @Override
    public GameStatus status() {
        return status;
    }

    @Override
    public Team turn() {
        throw new IllegalStateException("종료된 게임의 턴 정보는 가져올 수 없습니다.");
    }
}
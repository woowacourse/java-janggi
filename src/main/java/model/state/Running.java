package model.state;

import model.GameStatus;
import model.JanggiReferee;
import model.JanggiState;
import model.Team;
import model.board.Board;

public class Running implements JanggiState {
    private final Team turn;
    private final GameStatus status;

    public Running(Team turn, GameStatus status) {
        this.turn = turn;
        this.status = status;
    }

    @Override
    public JanggiState next(Board board) {
        Team nextTurn = this.turn.opposite();
        if (!board.isAliveGeneral(nextTurn)) {
            return new Finished(this.turn, GameStatus.DONE);
        }

        if (JanggiReferee.checkBigJang(board, nextTurn)) {
            return new Running(nextTurn, GameStatus.BIG_JANG);
        }
        return new Running(nextTurn, status);
    }

    @Override
    public Team resolveWinner(Board board) {
        throw new IllegalStateException("게임 진행 중에는 승자를 알 수 없습니다.");
    }

    @Override
    public boolean canPlaying() {
        return true;
    }

    @Override
    public GameStatus status() {
        return status;
    }

    @Override
    public Team turn() {
        return turn;
    }
}
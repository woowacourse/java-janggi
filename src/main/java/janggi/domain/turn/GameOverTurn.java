package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;

public class GameOverTurn extends Turn {

    private final Team winner;

    public GameOverTurn(Team winner) {
        this.winner = winner;
    }

    @Override
    public Turn move(Position source, Position target, Board board) {
        throw new IllegalArgumentException("[ERROR] 이미 종료된 게임입니다.");
    }

    @Override
    public Team getTeam() {
        return winner;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

package janggi.domain;

import janggi.domain.status.ChoTurn;
import janggi.domain.status.GameStatus;
import janggi.domain.status.Team;

public class JanggiGame {

    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board) {
        this.board = board;
        this.gameStatus = new ChoTurn();
    }

    public boolean isFinished() {
        return gameStatus.isFinished();
    }

    public Team getWinner() {
        if (!gameStatus.isFinished()) {
            throw new RuntimeException("게임이 아직 끝나지 않았습니다.");
        }
        return gameStatus.getTeam();
    }

    public void play(Point from, Point to) {
        this.gameStatus = gameStatus.move(from, to, board);
    }
}

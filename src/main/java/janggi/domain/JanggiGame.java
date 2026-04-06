package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.GameStatus;
import janggi.domain.status.Team;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private GameStatus gameStatus;
    private final Boards boards;

    public JanggiGame(Board board) {
        this.gameStatus = new ChoTurn();
        this.boards = new Boards(board);
    }

    public JanggiGame(Board board, GameStatus gameStatus) {
        this.boards = new Boards(board);
        this.gameStatus = gameStatus;
    }

    public boolean isFinished() {
        return gameStatus.isFinished();
    }

    public Team getWinner() {
        if (!gameStatus.isFinished()) {
            throw new IllegalStateException("[ERROR] 게임이 아직 끝나지 않았습니다.");
        }
        return gameStatus.getTeam();
    }

    public List<List<Piece>> getBoardStatus() {
        return boards.getPoints();
    }

    public void play(Point from, Point to) {
        this.gameStatus = gameStatus.move(from, to, boards);
    }

    public Team currentTurn() {
        return gameStatus.getTeam();
    }

    public Map<Point, Piece> boardStatus() {
        return boards.getBoardStatus();
    }

    public int scoreOf(Team team) {
        return boards.scoreOf(team);
    }
}

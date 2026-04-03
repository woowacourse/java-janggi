package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.status.GameStatus;
import janggi.domain.status.Team;
import java.util.Map;

public class JanggiGame {

    private static final double BONUS_SCORE = 1.5;
    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board, GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    public boolean isFinished() {
        return gameStatus.isFinished();
    }

    public Team getWinner() {
        if (!gameStatus.isFinished()) {
            throw new RuntimeException("[ERROR] 게임이 아직 끝나지 않았습니다.");
        }
        return gameStatus.getTeam();
    }

    public Map<Point, Piece> getBoardStatus() {
        return board.getPieces();
    }

    public void play(Point from, Point to) {
        this.gameStatus = gameStatus.move(from, to, board);
    }

    public Team getTeam() {
        return gameStatus.getTeam();
    }

    public double getChoScore() {
        return board.calculateScore(Team.CHO);
    }

    public double getHanScore() {
        return board.calculateScore(Team.HAN) + BONUS_SCORE;
    }
}

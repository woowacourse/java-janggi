package domain.game;

import domain.board.BoardState;
import domain.board.JanggiBoard;
import domain.point.Point;
import domain.team.Team;
import dto.MoveDTO;

public class Game {

    private final JanggiBoard janggiBoard;
    private Boolean isGameRunning;
    private Team turn;

    public Game(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
        this.isGameRunning = true;
        this.turn = Team.CHO;
    }

    public void processTurn(MoveDTO move) {
        Point from = move.getFrom();
        Point to = move.getTo();

        checkCurrentTurnTeam(from);
        janggiBoard.tryToMove(from, to);
        isGameRunning = janggiBoard.isGameRunning();
        turn = turn.nextTurn();
    }

    private void checkCurrentTurnTeam(Point point) {
        if (janggiBoard.findIntersection(point).getTeam().equals(turn)) {
            return;
        }
        throw new IllegalArgumentException(
                turn.getKoreanTeamName() + "(" + turn.getChineseTeamName() + ")" + "의 기물만 움직일 수 있습니다.");
    }

    public Team currentTurn() {
        return turn;
    }

    public Team getWinnerTeam() {
        return turn.nextTurn();
    }

    public BoardState boardStatus() {
        return janggiBoard.boardStatus();
    }

    public boolean isRunning() {
        return isGameRunning;
    }

}

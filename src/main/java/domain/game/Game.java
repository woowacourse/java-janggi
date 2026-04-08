package domain.game;

import domain.board.BoardState;
import domain.board.JanggiBoard;
import domain.point.Point;
import domain.team.Team;

public class Game {
    private final JanggiBoard janggiBoard;
    private boolean isGameRunning;
    private Team turn;

    public Game(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
        this.isGameRunning = true;
        this.turn = Team.CHO;
    }

    public void processTurn(MoveCommand move) {
        Point from = move.getFrom();
        Point to = move.getTo();
        validateTurn(from);
        janggiBoard.tryToMove(from, to);
        isGameRunning = janggiBoard.isGameRunning();
        turn = turn.nextTurn();
    }

    private void validateTurn(Point point) {
        if (janggiBoard.isSameTeamAt(point, turn)) {
            return;
        }
        throw new IllegalArgumentException("현재 턴에 해당하는 팀의 기물만 움직일 수 있습니다.");
    }

    public Team currentTurn() {
        return turn;
    }

    public boolean isRunning() {
        return isGameRunning;
    }

    public BoardState getBoardState() {
        return janggiBoard.boardState();
    }
}

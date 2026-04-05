package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.Turn;
import janggi.domain.piece.Score;
import janggi.domain.piece.Team;

public class JanggiGame {

    private final Board board;
    private Turn turn = new Turn(Team.HAN);

    public JanggiGame(Board board) {
        this.board = board;
    }

    public void move(Position from, Position to) {
        board.move(from, to, turn.getTeam());
        turn = turn.changeTurn();
    }

    public boolean isOver() {
        return board.isGeneralCaptured(turn.getTeam());
    }

    public Score calculateScore(Team team) {
        return board.calculateScore(team);
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}

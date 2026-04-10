package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.MoveResult;
import janggi.domain.board.Position;
import janggi.domain.board.Turn;
import janggi.domain.piece.Score;
import janggi.domain.piece.Team;

public class JanggiGame {

    private final Board board;
    private Turn turn;

    public JanggiGame(Board board, Team team) {
        this.board = board;
        this.turn = new Turn(team);
    }

    public MoveResult move(Position from, Position to) {
        MoveResult result = board.move(from, to, turn.getTeam());
        turn = turn.changeTurn();
        return result;
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

    public String getTurnName() {
        return turn.getTeamName();
    }
}

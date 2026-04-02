package domain.game;

import domain.board.BoardState;
import domain.board.JanggiBoard;
import domain.point.Point;
import domain.team.Team;
import dto.MoveDTO;

public class Game {

    private final JanggiBoard janggiBoard;
    private Team turn;

    public Game(JanggiBoard janggiBoard) {
        this.janggiBoard = janggiBoard;
        this.turn = Team.CHO;
    }

    public void processTurn(MoveDTO move) {
        Point from = move.getFrom();
        Point to = move.getTo();
        janggiBoard.tryToMove(from, to);

        turn = turn.nextTurn();
    }

    public Team currentTurn() {
        return turn;
    }

    public BoardState boardStatus() {
        return janggiBoard.boardStatus();
    }

}

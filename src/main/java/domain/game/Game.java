package domain.game;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.point.Point;
import domain.team.Team;
import dto.BoardStatusDTO;
import dto.MoveDTO;
import java.util.Map;

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

    public Map<Point, Intersection> boardStatus() {
        return janggiBoard.boardStatus();
    }

}

package team.janggi.domain;

import java.util.Map;
import team.janggi.domain.board.Board;

public class JanggiGame {

    private final Board board;
    private Team currentTurn;

    public JanggiGame(Board board, Team currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public void doTurn(Position from, Position to) {
        try {
            board.move(currentTurn, from, to);
            nextTeam();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void nextTeam() {
        this.currentTurn = Map.of(Team.CHO, Team.HAN, Team.HAN, Team.CHO).get(currentTurn);
    }

    public boolean isGameOver() {
        return board.isKingDisappeared();
    }

    public Board getBoard() {
        return board;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }
}

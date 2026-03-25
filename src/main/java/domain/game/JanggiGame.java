package domain.game;

import domain.state.GameState;
import domain.vo.Arrangements;
import domain.vo.Team;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;

    public void setupBoard(Arrangements arrangements) {
        this.board = Board.of(arrangements);
        this.turn = new Turn(Team.CHO);
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}

package domain.game;

import domain.state.GameState;
import domain.vo.Arrangements;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;

    public void setupBoard(Arrangements arrangements) {
        this.board = Board.of(arrangements);
    }
}

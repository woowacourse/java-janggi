package config;

import domain.board.Board;
import janggigame.JanggiGame;

public class DiConfig {

    private final Board board = new Board();

    public JanggiGame janggiGame() {
        return new JanggiGame(board);
    }
}

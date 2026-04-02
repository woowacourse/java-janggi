package janggi.domain;

import janggi.domain.Turn.ChoTurn;
import janggi.domain.Turn.GameState;
import janggi.domain.board.Board;
import janggi.domain.position.Position;

public class JanggiGameManager {
    private GameState currentState;

    public JanggiGameManager(Board board) {
        this.currentState = new ChoTurn(board);
    }

    public void move(Position from, Position to) {
        currentState = currentState.move(from, to);
    }
}

package janggi.domain;

import janggi.domain.Turn.ChoTurn;
import janggi.domain.Turn.GameState;
import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import java.util.Map;

public class JanggiGameManager {
    private GameState currentState;

    public JanggiGameManager(Board board) {
        this.currentState = new ChoTurn(board);
    }

    public void move(Position from, Position to) {
        currentState = currentState.move(from, to);
    }

    public boolean isFinished() {
        return currentState.isFinished();
    }

    public Map<Position, Space> captureBoard() {
        return currentState.captureBoard();
    }
}

package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;

public class GameContext {

    private GameState gameState;

    private GameContext(GameState state) {
        this.gameState = state;
    }

    public static GameContext createInProgress(List<Piece> alivePieces, Side currentSide) {
        return new GameContext(new InProgress(currentSide, alivePieces));
    }

    public void changeState(GameState state) {
        this.gameState = state;
    }

    public boolean isInProgress() {
        return !this.gameState.isEnd();
    }

    public void update(Piece removedPiece) {
        this.gameState.update(this, removedPiece);
    }

    public Side getCurrentSide() {
        return this.gameState.getCurrentSide();
    }

    public Side getWinner() {
        return this.gameState.getWinner();
    }
}

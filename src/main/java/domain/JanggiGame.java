package domain;

import domain.board.Position;
import domain.gameState.State;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class JanggiGame {
    private State state;

    public JanggiGame(State state) {
        this.state = state;
    }

    public void move(Piece piece, Position source, Position destination) {
        this.state = state.movePiece(piece, source, destination);
    }

    public PieceColor getTurnColor() {
        return this.state.getColor();
    }

    public boolean isFinished() {
        return false;
    }
}

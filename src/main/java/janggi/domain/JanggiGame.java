package janggi.domain;

import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;

public class JanggiGame {
    private State state;

    public JanggiGame(State state) {
        this.state = state;
    }

    public void move(PieceType pieceType, Position source, Position destination) {
        this.state = state.movePiece(pieceType, source, destination);
    }

    public PieceColor getTurnColor() {
        return this.state.getColor();
    }

    public boolean isFinished() {
        return state.isFinished();
    }
}

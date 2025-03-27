package domain;

import domain.board.Position;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.state.State;

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

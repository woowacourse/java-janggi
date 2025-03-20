package domain.gameState;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class Finished implements State{
    private final PieceColor pieceColor;

    public Finished(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    @Override
    public State movePiece(Piece piece, Position source, Position destination) {
        throw new IllegalArgumentException("게임이 끝난 후에는 움직일 수 없습니다.");
    }

    @Override
    public PieceColor getColor() {
        return pieceColor;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

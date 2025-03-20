package janggi.domain.gameState;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class RedTurn extends InProgress {
    public RedTurn(Board board) {
        super(board, PieceColor.RED);
    }

    @Override
    protected State getNextTurn() {
        return new BlueTurn(board);
    }

    @Override
    public void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 빨간색이 아닙니다.");
        }
    }

}

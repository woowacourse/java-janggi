package janggi.domain.gameState;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class BlueTurn extends InProgress {

    public BlueTurn(Board board) {
        super(board, PieceColor.BLUE);
    }

    @Override
    protected State getNextTurn() {
        return new RedTurn(board);
    }

    @Override
    public void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 파란색이 아닙니다.");
        }
    }
}

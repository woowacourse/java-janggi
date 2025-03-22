package janggi.domain.gameState;

import janggi.domain.board.PlayingBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class BlueTurn extends InProgress {

    public BlueTurn(PlayingBoard playingBoard) {
        super(playingBoard, PieceColor.BLUE);
    }

    @Override
    protected State getNextTurn() {
        return new RedTurn(playingBoard);
    }

    @Override
    public void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 파란색이 아닙니다.");
        }
    }
}

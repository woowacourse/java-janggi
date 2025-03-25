package janggi.domain.gameState;

import janggi.domain.board.PlayingBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;

public class RedTurn extends InProgress {
    public RedTurn(PlayingBoard playingBoard) {
        super(playingBoard, TeamColor.RED);
    }

    @Override
    protected State getNextTurn() {
        return new BlueTurn(playingBoard);
    }

    @Override
    public void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 빨간색이 아닙니다.");
        }
    }
}

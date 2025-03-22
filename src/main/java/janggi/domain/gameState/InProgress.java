package janggi.domain.gameState;

import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;

public abstract class InProgress implements State {
    final PlayingBoard playingBoard;
    final PieceColor turnColor;

    public InProgress(PlayingBoard playingBoard, PieceColor turnColor) {
        this.playingBoard = playingBoard;
        this.turnColor = turnColor;
    }

    @Override
    public final State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = playingBoard.getPieceBy(source);
        validateIsMyPiece(sourcePiece);

        Piece destinationPiece = playingBoard.getPieceBy(destination);
        playingBoard.move(pieceType, source, destination);

        if (destinationPiece.isPieceType(PieceType.GENERAL)) {
            return new Finished(turnColor);
        }
        return getNextTurn();
    }

    protected void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 " + turnColor + "색이 아닙니다.");
        }
    }

    protected abstract State getNextTurn();

    @Override
    public PieceColor getColor() {
        return turnColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

package janggi.domain.gameState;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;

public abstract class InProgress implements State{
    final Board board;
    final PieceColor turnColor;

    public InProgress(Board board, PieceColor turnColor) {
        this.board = board;
        this.turnColor = turnColor;
    }


    @Override
    public final State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = board.getPieceBy(source);
        validateIsMyPiece(sourcePiece);

        Piece destinationPiece = board.getPieceBy(destination);
        board.move(pieceType, source, destination);

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
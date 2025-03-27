package domain.gameState;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public abstract class Playing implements State {
    protected final Board board;
    protected final PieceColor pieceColor;

    protected Playing(Board board, PieceColor pieceColor) {
        this.board = board;
        this.pieceColor = pieceColor;
    }

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = board.getPieceBy(source);
        Piece destinationPiece = board.getPieceBy(destination);
        validateIsMyPieceColor(sourcePiece);

        board.movePiece(pieceType, source, destination);
        boolean isGeneral = destinationPiece.isSamePieceType(PieceType.GENERAL);

        if (isGeneral) {
            return new Finished(pieceColor);
        }
        return nextTurn(board);
    }

    @Override
    public PieceColor getColor() {
        return this.pieceColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void validateIsMyPieceColor(Piece sourcePiece) {
        if (sourcePiece.isOtherTeam(pieceColor)) {
            throw new IllegalArgumentException("움직이려는 기물이 본인팀이 아닙니다.");
        }
    }

    protected abstract State nextTurn(Board board);
}

package janggi.domain.gameState;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;

public class BlueTurn implements State {

    private final Board board;
    private final PieceColor pieceColor;

    public BlueTurn(Board board) {
        this.board = board;
        this.pieceColor = PieceColor.BLUE;
    }

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = board.getPieceBy(source);
        validateIsMyPiece(sourcePiece);

        Piece destinationPiece = board.getPieceBy(destination);
        board.move(pieceType, source, destination);
        boolean isGeneral = destinationPiece.isPieceType(PieceType.GENERAL);

        if(isGeneral) {
            return new Finished(pieceColor);
        }
        return new RedTurn(board);
    }

    @Override
    public PieceColor getColor() {
        return this.pieceColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != PieceColor.BLUE) {
            throw new IllegalArgumentException("움직이려는 기물이 파란색이 아닙니다.");
        }
    }
}

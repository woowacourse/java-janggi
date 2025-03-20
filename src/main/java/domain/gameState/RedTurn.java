package domain.gameState;

import domain.board.Board;
import domain.board.Position;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class RedTurn implements State {

    private final Board board;
    private final PieceColor pieceColor;

    public RedTurn(Board board) {
        this.board = board;
        pieceColor = PieceColor.RED;
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
        return new BlueTurn(board);
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
        if (piece.getColor() != PieceColor.RED) {
            throw new IllegalArgumentException("움직이려는 기물이 빨간색이 아닙니다.");
        }
    }

}

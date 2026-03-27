package model.policy;

import model.board.Board;
import model.move.Move;
import model.pieces.Cannon;
import model.pieces.Piece;
import model.pieces.PieceType;

public class CannonDestinationPolicy implements DestinationPolicy {

    @Override
    public boolean validate(Move move, Board board) {
        Piece fromPiece = board.findPiece(move.from());
        Piece toPiece = board.findPiece(move.to());

        return toPiece == null || (fromPiece.country() != toPiece.country() || toPiece.pieceType() == PieceType.CANNON);
    }
}

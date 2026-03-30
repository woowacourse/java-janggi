package model.policy;

import model.board.Board;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;

public class CannonPathPolicy extends PathPolicy {
    private static final int JUMP_PIECE = 1;
    private int count = 0;

    @Override
    public boolean check(Position pos, Board board) {
        if (!board.isPathEmpty(pos)) {
            count++;
        }

        if (!board.isPathEmpty(pos) && board.findPiece(pos).pieceType() == PieceType.CANNON) {
            return false;
        }

        return count <= JUMP_PIECE;
    }

    @Override
    public boolean validate(Move move, Board board) {
        Piece fromPiece = board.findPiece(move.from());
        Piece toPiece = board.findPiece(move.to());

        return isValid() && (toPiece == null || (fromPiece.country() != toPiece.country()
                && toPiece.pieceType() != PieceType.CANNON));
    }

    public boolean isValid() {
        return count >= JUMP_PIECE;
    }
}

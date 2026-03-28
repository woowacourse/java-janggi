package model.policy;

import model.board.Board;
import model.pieces.PieceType;
import model.position.Position;

public class CannonPathPolicy extends PathPolicy {
    private int count = 0;

    @Override
    public boolean check(Position pos, Board board) {
        if (!board.isPathEmpty(pos)) {
            count++;
        }

        if (!board.isPathEmpty(pos) && board.findPiece(pos).pieceType() == PieceType.CANNON) {
            return false;
        }

        return count <= 1;
    }

    @Override
    public boolean isValid() {
        return count >= 1;
    }
}

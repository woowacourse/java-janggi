package model.policy;

import model.board.Board;
import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class DefaultPathPolicy extends PathPolicy {
    @Override
    public boolean check(Position pos, Board board) {
        return board.isPathEmpty(pos);
    }

    @Override
    public boolean validate(Move move, Board board) {
        Piece fromPiece = board.findPiece(move.from());
        Piece toPiece = board.findPiece(move.to());

        return toPiece == null || (fromPiece.country() != toPiece.country());
    }
}

package model.policy;

import java.util.List;
import model.board.Board;
import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class DefaultPathPolicy extends PathPolicy {
    @Override
    public boolean validatePath(List<Position> path, Board board) {
        return board.countPiecesOnPath(path) == 0;
    }

    @Override
    public boolean validateDestination(Move move, Board board) {
        Piece fromPiece = board.findPiece(move.from());
        Piece toPiece = board.findPiece(move.to());

        if (toPiece == null) {
            return true;
        }

        if (fromPiece.country() == toPiece.country()) {
            throw new IllegalArgumentException("[ERROR] 아군 기물입니다.");
        }

        return true;
    }
}

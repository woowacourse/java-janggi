package model.policy;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class DefaultPathPolicy extends PathPolicy {
    @Override
    public boolean validatePath(List<Position> path, Board board) {
        return board.countPiecesOnPath(path) == 0;
    }

    @Override
    public boolean validateDestination(Move move, Board board, Country country) {
        board.findPiece(move.to())
                .ifPresent(piece -> validatePiece(piece, country));

        return true;
    }

    private void validatePiece(Piece toPiece, Country country) {
        if (country == toPiece.country()) {
            throw new IllegalArgumentException("[ERROR] 아군 기물입니다.");
        }
    }
}

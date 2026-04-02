package model.policy;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class SoldierPathPolicy extends PathPolicy {
    @Override
    public boolean validatePath(List<Position> path, Board board) {
        return true;
    }

    @Override
    public boolean validateDestination(Move move, Board board, Country country) {
        Piece fromPiece = board.findPiece(move.from());
        if (country.forbidden().contains(move.direction())) {
            throw new IllegalArgumentException("[ERROR] 병사는 뒤로 갈 수 없습니다.");
        }

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

package model.policy;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.move.Direction;
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
        Direction direction = move.direction();
        if (country.forbidden().contains(direction)) {
            throw new IllegalArgumentException("[ERROR] 병사는 뒤로 갈 수 없습니다.");
        }
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

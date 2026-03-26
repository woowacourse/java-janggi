package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.Map;

import static util.ErrorMessage.NOT_EXISTS_PIECE;

public class EmptyPiece implements Piece {

    public static final EmptyPiece INSTANCE = new EmptyPiece();

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Team team() {
        return null;
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        throw new PieceException(NOT_EXISTS_PIECE.getMessage());
    }

    @Override
    public void isSameTeam(Piece piece) {
        throw new PieceException(NOT_EXISTS_PIECE.getMessage());
    }

    @Override
    public boolean isSameTeam(Team team) {
        return false;
    }
}

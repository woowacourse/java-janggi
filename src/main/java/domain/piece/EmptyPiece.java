package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

import static util.ErrorMessage.NOT_EXISTS_PIECE;

public class EmptyPiece extends Piece {

    public EmptyPiece(Team team) {
        super(team);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public Team team() {
        return Team.NONE;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        throw new PieceException(NOT_EXISTS_PIECE.getMessage());
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        throw new PieceException(NOT_EXISTS_PIECE.getMessage());
    }
}

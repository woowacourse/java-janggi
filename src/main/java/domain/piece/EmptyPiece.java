package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.Map;

public class EmptyPiece extends Piece {

    private static final String NOT_EXISTS_PIECE = "기물이 존재하지 않습니다.";

    public EmptyPiece(Team team) {
        super(team);
    }

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
        throw new PieceException(NOT_EXISTS_PIECE);
    }
}

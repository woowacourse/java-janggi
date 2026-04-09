package domain.piece;

import domain.move.MoveContext;
import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.List;

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
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        throw new PieceException(NOT_EXISTS_PIECE);
    }

    @Override
    public List<Coordination> resolvePath(MoveContext moveContext) {
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        throw new PieceException(NOT_EXISTS_PIECE);
    }
}

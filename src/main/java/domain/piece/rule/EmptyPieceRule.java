package domain.piece.rule;

import domain.coordination.Coordination;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.error.PieceException;
import java.util.List;

public class EmptyPieceRule implements PieceRule {

    private static final String NOT_EXISTS_PIECE = "기물이 존재하지 않습니다.";

    @Override
    public void validate(Coordination from, Coordination to, Team team) {
        throw new PieceException(NOT_EXISTS_PIECE);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to, Team team) {
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        throw new PieceException(NOT_EXISTS_PIECE);
    }
}

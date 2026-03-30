package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.gimul.Piece;
import java.util.List;

public abstract class DiagonalMovePiece extends Piece {

    protected DiagonalMovePiece(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(
            List<Piece> gimulsOnPath,
            Piece pieceAtTo
    ) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }
}

package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import java.util.List;

public class Pho extends StraightMovePiece {
    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.size() == 1
                && !(piecesOnPath.getFirst() instanceof Pho)
                && !this.isSameTeam(pieceAtTo);
    }
}

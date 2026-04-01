package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import java.util.List;

public class Cha extends StraightMovePiece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }
}

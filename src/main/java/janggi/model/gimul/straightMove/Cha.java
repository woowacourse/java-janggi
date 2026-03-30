package janggi.model.gimul.straightMove;

import janggi.model.Team;
import janggi.model.gimul.Piece;
import java.util.List;

public class Cha extends StraightMovePiece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Piece> gimulsOnPath, Piece pieceAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }
}

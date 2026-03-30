package janggi.model.gimul.straightMove;

import janggi.model.Team;
import janggi.model.gimul.Piece;
import java.util.List;

public class Pho extends StraightMovePiece {
    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Piece> gimulsOnPath, Piece pieceAtTo) {
        return gimulsOnPath.size() == 1
                && !(gimulsOnPath.getFirst() instanceof Pho)
                && !this.isSameTeam(pieceAtTo);
    }
}

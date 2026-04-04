package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractStraightPiece;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public class Cha extends AbstractStraightPiece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    public boolean canMove(Route route) {
        return route.isEmpty();
    }
}

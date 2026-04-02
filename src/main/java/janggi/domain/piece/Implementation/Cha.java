package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractStraightPiece;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public class Cha extends AbstractStraightPiece {

    private static final int SCORE = 13;

    public Cha(Team team) {
        super(SCORE, team, PieceType.CHA);
    }

    public boolean canMove(Route route) {
        return route.isEmpty();
    }
}

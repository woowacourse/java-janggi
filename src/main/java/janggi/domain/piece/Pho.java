package janggi.domain.piece;

import janggi.domain.point.Route;
import janggi.domain.status.Team;

public class Pho extends AbstractStraightPiece {

    private static final int SCORE = 7;

    public Pho(Team team) {
        super(SCORE, team, PieceType.PHO);
    }

    @Override
    public boolean canMove(Route route) {
        if (route.hasObstacle()) {
            return false;
        }
        return !route.hasSameType(PieceType.PHO);
    }

    @Override
    public boolean canCapture(Piece target) {
        return !target.isSameType(PieceType.PHO);
    }
}

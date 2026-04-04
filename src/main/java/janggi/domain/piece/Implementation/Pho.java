package janggi.domain.piece.Implementation;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.template.AbstractStraightPiece;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public class Pho extends AbstractStraightPiece {

    public Pho(Team team) {
        super(team, PieceType.PHO);
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

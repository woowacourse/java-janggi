package model.piece.goongsungpiece;

import model.Point;
import model.Team;
import model.janggiboard.ChaPhoGoongsungRule;
import model.piece.Piece;

public abstract class GoongsungAdvantagePiece extends Piece {
    private final ChaPhoGoongsungRule chaPhoGoongsungRule;

    public GoongsungAdvantagePiece(Team team) {
        super(team);
        chaPhoGoongsungRule = new ChaPhoGoongsungRule();
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        if (chaPhoGoongsungRule.isStartedInGoongsung(beforePoint)
                && chaPhoGoongsungRule.containsCenterPoint(calculatePath(beforePoint, targetPoint))) {
            return true;
        }

        boolean isStraightMove = beforePoint.x() == targetPoint.x() || beforePoint.y() == targetPoint.y();
        boolean isSamePoint = beforePoint.equals(targetPoint);

        return isStraightMove && !isSamePoint;
    }

}

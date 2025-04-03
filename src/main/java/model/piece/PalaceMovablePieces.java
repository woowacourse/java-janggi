package model.piece;

import model.Point;
import model.Team;

public abstract class PalaceMovablePieces extends Piece {

    protected PalaceMovablePieces(Team team, PieceName pieceName,Score score) {
        super(team, pieceName,score);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        if (Palace.ALL_PALACE.getPoints().contains(beforePoint)) {
            validateGungCross(beforePoint, targetPoint);
            return true;
        }
        validateMovement(beforePoint, targetPoint);
        return true;
    }

    public abstract void validateGungCross(Point beforePoint, Point targetPoint);

    public abstract void validateMovement(Point beforePoint, Point targetPoint);
}

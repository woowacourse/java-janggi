package model.piece;

import model.Point;
import model.Team;

public abstract class FixedPalacePieces extends Piece {

    private static final int PALACE_MIN_POINT_X = 3;
    private static final int PALACE_MAX_POINT_X = 5;

    protected FixedPalacePieces(Team team, PieceName pieceName, Score score) {
        super(team, pieceName, score);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        validatePalaceRange(targetPoint);
        validateGungMove(beforePoint, targetPoint);
        return true;
    }

    public void validatePalaceRange(Point targetPoint) {
        if (targetPoint.x() < PALACE_MIN_POINT_X || targetPoint.x() > PALACE_MAX_POINT_X) {
            throw new IllegalArgumentException("궁 밖에 나갈 수 없습니다.");
        }
        if (targetPoint.y() < getTeam().getYPalaceMinimum() || targetPoint.y() > getTeam().getYPalaceMaximum()) {
            throw new IllegalArgumentException("궁 밖에 나갈 수 없습니다.");
        }
    }

    protected abstract void validateGungMove(Point beforePoint, Point targetPoint);
}

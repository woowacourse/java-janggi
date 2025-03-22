package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public class Sang extends Piece {
    public Sang(Team team) {
        super(team);
        pieceName = PieceName.SANG;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);

        return Math.pow(vectorX,2) + Math.pow(vectorY,2) == 13;
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);

        int unitVectorX = getUnitVector(vectorX);
        int unitVectorY = getUnitVector(vectorY);

        Point middlePoint1 = new Point(targetPoint.x() - unitVectorX, targetPoint.y() - unitVectorY);
        Point middlePoint2 = new Point(targetPoint.x() - unitVectorX * 2, targetPoint.y() - unitVectorY * 2);
        Point endPoint = new Point(targetPoint.x(), targetPoint.y());

        Path path = new Path();
        path.addPoint(middlePoint1);
        path.addPoint(middlePoint2);
        path.addPoint(endPoint);
        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 2) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values().stream().findFirst().get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet().stream().findFirst().get().getTeam() != this.team;
        }
        return true;
    }

}

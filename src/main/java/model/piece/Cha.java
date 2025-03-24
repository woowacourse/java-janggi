package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team,PieceName.CHA);
    }

    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        boolean isStraightMove = beforePoint.x() == targetPoint.x()
                || beforePoint.y() == targetPoint.y();
        boolean isSamePoint = beforePoint.x() == targetPoint.x()
                && beforePoint.y() == targetPoint.y();
        return isStraightMove && !isSamePoint;
    }
    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);

        int unitVectorX = getUnitVector(vectorX);
        int unitVectorY = getUnitVector(vectorY);

        Path path = new Path();

        for (int i = 0; i < Math.max(vectorY, vectorX); i++) {
            path.addPoint(new Point(targetPoint.x() - unitVectorX * i, targetPoint.y() - unitVectorY * i));
            }

        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.isEmpty()) {
            return true;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .findFirst()
                    .get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return false;
    }
}

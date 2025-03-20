package model.piece;

import java.util.Map;
import java.util.Map.Entry;
import model.Path;
import model.Point;
import model.Team;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team);
        pieceName = PieceName.PHO;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        return !((beforePoint.x() == targetPoint.x() && beforePoint.y() == targetPoint.y()) || (
                beforePoint.x() != targetPoint.x() && beforePoint.y() != targetPoint.y()));
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = targetPoint.x() - beforePoint.x();
        int vectorY = targetPoint.y() - beforePoint.y();

        Path path = new Path();

        if (vectorX == 0) {
            int unitVectorY = vectorY / Math.abs(vectorY);
            for (int i = 0; i < Math.abs(vectorY); i++) {
                path.addPoint(new Point(targetPoint.x(), targetPoint.y() - unitVectorY * i));
            }
        }

        if (vectorY == 0) {
            int unitVectorX = vectorX / Math.abs(vectorX);
            for (int i = 0; i < Math.abs(vectorX); i++) {
                path.addPoint(new Point(targetPoint.x() - unitVectorX * i, targetPoint.y()));
            }
        }
        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 3) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (piecesOnPathWithTargetOrNot.values()
                    .stream().findFirst().get() || piecesOnPathWithTargetOrNot.keySet()
                    .stream().findFirst().get() instanceof Pho) {
                return false;
            }
            return true;
        }

        if (piecesOnPathWithTargetOrNot.size() == 2) {
            if (piecesOnPathWithTargetOrNot
                    .keySet()
                    .stream()
                    .anyMatch(piece -> piece instanceof Pho)) {
                return false;
            }

            if (piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .noneMatch(isTargetPoint -> isTargetPoint)) {
                return false;
            }

            if (piecesOnPathWithTargetOrNot.entrySet().stream()
                    .filter(Entry::getValue)
                    .findFirst()
                    .get()
                    .getKey()
                    .getTeam() == this.team) {
                return false;
            }
            return true;
        }
        return true;
    }


}
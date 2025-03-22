package model.piece;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
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
        List<Integer> horizontal = List.of(-2, 2, 3, 3, 2, -2, -3, -3);
        List<Integer> vertical = List.of(3, 3, 2, -2, -3, -3, -2, 2);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x()
                        && vertical.get(i) + beforePoint.y() == targetPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = targetPoint.x() - beforePoint.x();
        int vectorY = targetPoint.y() - beforePoint.y();

        int unitVectorX = vectorX / Math.abs(vectorX);
        int unitVectorY = vectorY / Math.abs(vectorY);

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
        return true;
    }
}

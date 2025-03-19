package model;

import java.util.List;
import java.util.stream.IntStream;

public class Sang extends Piece {
    public Sang(Team team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point afterPoint) {
        List<Integer> horizontal = List.of(-2, 2, 3, 3, 2, -2, -3, -3);
        List<Integer> vertical = List.of(3, 3, 2, -2, -3, -3, -2, 2);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == afterPoint.x() && vertical.get(i) + beforePoint.y() == afterPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point afterPoint) {
        int vectorX = afterPoint.x() - beforePoint.x();
        int vectorY = afterPoint.y() - beforePoint.y();

        int unitVectorX = vectorX / Math.abs(vectorX);
        int unitVectorY = vectorY / Math.abs(vectorY);

        Point middlePoint1 = new Point(afterPoint.x() - unitVectorX, afterPoint.y() - unitVectorY);
        Point middlePoint2 = new Point(afterPoint.x() - unitVectorX * 2, afterPoint.y() - unitVectorY * 2);
        Point endPoint = new Point(afterPoint.x(), afterPoint.y());

        Path path = new Path();
        path.addPoint(middlePoint1);
        path.addPoint(middlePoint2);
        path.addPoint(endPoint);
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }

}

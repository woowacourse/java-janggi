package model;

import java.util.List;
import java.util.stream.IntStream;

public class Sang extends Piece {
    public Sang(String team) {
        super(team);
    }

    @Override
    public boolean canMove(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(-2, 2, 3, 3, 2, -2, -3, -3);
        List<Integer> vertical = List.of(3, 3, 2, -2, -3, -3, -2, 2);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }

    @Override
    public Path calculatePath(int beforeX, int beforeY, int afterX, int afterY) {
        int vectorX = afterX - beforeX;
        int vectorY = afterY - beforeY;

        int unitVectorX = vectorX / Math.abs(vectorX);
        int unitVectorY = vectorY / Math.abs(vectorY);

        Point middlePoint1 = new Point(afterX - unitVectorX, afterY - unitVectorY);
        Point middlePoint2 = new Point(afterX - unitVectorX * 2, afterY - unitVectorY * 2);
        Point endPoint = new Point(afterX, afterY);

        Path path = new Path();
        path.addPoint(middlePoint1);
        path.addPoint(middlePoint2);
        path.addPoint(endPoint);
        return path;
    }
}

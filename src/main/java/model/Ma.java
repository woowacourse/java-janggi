package model;

import java.util.List;
import java.util.stream.IntStream;

public class Ma extends Piece {

    public Ma(String team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(-1, 1, 2, 2, 1, -1, -2, -2);
        List<Integer> vertical = List.of(2, 2, 1, -1, -2, -2, -1, 1);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }

    @Override
    public Path calculatePath(int beforeX, int beforeY, int afterX, int afterY) {
        // 1. 이동 벡터를 구한다
        // 2. 이동 단위 벡터를 구한다.
        // 3. 단위 벡터 역벡터를 도착점에 더한다.
        // 3.이랑 도착점을 Path에 담아서 리턴

        int vectorX = afterX - beforeX;
        int vectorY = afterY - beforeY;

        int unitVectorX = vectorX / Math.abs(vectorX);
        int unitVectorY = vectorY / Math.abs(vectorY);

        Point middlePoint = new Point(afterX - unitVectorX, afterY - unitVectorY);
        Point endPoint = new Point(afterX, afterY);

        Path path = new Path();
        path.addPoint(middlePoint);
        path.addPoint(endPoint);
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }
}

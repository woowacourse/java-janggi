package model;

import java.util.List;
import java.util.stream.IntStream;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point afterPoint) {
        int moveForward = 1;

        if (team == Team.RED){
            moveForward = -1;
        }

        List<Integer> horizontal = List.of(0, -1, 1);
        List<Integer> vertical = List.of(moveForward, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == afterPoint.x() && vertical.get(i) + beforePoint.y() == afterPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point afterPoint) {
        Path path = new Path();
        path.addPoint(new Point(afterPoint.x(), afterPoint.y()));
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }

}

package model;

import java.util.List;
import java.util.stream.IntStream;

public class Jang extends Piece {

    public Jang(String team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(0, 0, -1, 1);
        List<Integer> vertical = List.of(1, -1, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }

    @Override
    public Path calculatePath(int beforeX, int beforeY, int afterX, int afterY) {
        Path path = new Path();
        path.addPoint(new Point(afterX, afterY));
        return path;
    }

    @Override
    public boolean canMove(int size) {
        return false;
    }
}

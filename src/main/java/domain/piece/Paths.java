package domain.piece;

import domain.piece.coordiante.Coordinate;
import java.util.List;

public class Paths {

    private final List<Coordinate> paths;

    public Paths(List<Coordinate> paths) {
        this.paths = paths;
    }

    public void canMove(Coordinate to) {
        if (!paths.contains(to)) {
            throw new IllegalArgumentException("[ERROR] 이동 불가능한 위치입니다.");
        }
    }
}

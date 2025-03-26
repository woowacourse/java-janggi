package piece;

import java.util.List;

import direction.Direction;
import direction.Point;

public abstract class Soldier extends Piece {

    public Soldier(String name, Point point) {
        super(name, point);
    }

    public abstract List<Direction> getPaths();

    @Override
    public void validateDestination(Point to) {
        getPaths().stream()
                .filter(direction -> currentPosition.plus(direction).equals(to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }

    @Override
    public void checkPaths(Pieces allPieces, Point to) {

    }
}

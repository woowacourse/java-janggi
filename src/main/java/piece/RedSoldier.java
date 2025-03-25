package piece;

import static direction.Direction.DOWN;
import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;

import direction.Direction;
import direction.Point;
import java.util.List;

public class RedSoldier extends Soldier {
    public RedSoldier(String name, Point point) {
        super(name, point);
    }

    public List<Direction> getPaths() {
        return List.of(LEFT, RIGHT, DOWN);
    }
}

package piece;

import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;

import direction.Direction;
import direction.Point;
import java.util.List;

public class GreenSoldier extends Soldier {

    public GreenSoldier(String name, Point point) {
        super(name, point);
    }

    public List<Direction> getPaths() {
        return List.of(LEFT, RIGHT, UP);
    }
}

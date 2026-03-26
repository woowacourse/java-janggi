package domain.piece.move;

import java.util.Collections;
import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Direction> getDirections() {
        return Collections.unmodifiableList(directions);
    }

}

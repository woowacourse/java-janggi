package domain.piece.movement;

import domain.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Movements {

    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = new ArrayList<>(movements);
    }

    public void addGungMovement(Coordinate from) {
        gungCenter(from);
        gungRightDownCorner(from);
        gungDownLeftCorner(from);
        gungUpRightCorner(from);
        gungUpLeftCorner(from);
    }

    private void gungRightDownCorner(Coordinate from) {
        if (from.isGungDownRightCorner()) {
            this.movements.add(Movement.UP_LEFT);
        }
    }

    private void gungDownLeftCorner(Coordinate from) {
        if (from.isGungDownLeftCorner()) {
            this.movements.add(Movement.UP_RIGHT);
        }
    }

    private void gungUpRightCorner(Coordinate from) {
        if (from.isGungUpRightCorner()) {
            this.movements.add(Movement.DOWN_LEFT);
        }
    }

    private void gungUpLeftCorner(Coordinate from) {
        if (from.isGungUpLeftCorner()) {
            this.movements.add(Movement.DOWN_RIGHT);
        }
    }

    private void gungCenter(Coordinate from) {
        if (from.isGungCenter()) {
            this.movements.add(Movement.DOWN_RIGHT);
            this.movements.add(Movement.DOWN_LEFT);
            this.movements.add(Movement.UP_LEFT);
            this.movements.add(Movement.UP_RIGHT);
        }
    }

    public List<Movement> getMovements() {
        return movements;
    }
}

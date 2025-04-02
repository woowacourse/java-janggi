package domain.piece.movement;

import domain.piece.coordiante.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Movements {

    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = new ArrayList<>(movements);
    }

    public void addMovementIfInGung(Coordinate from) {
        addIfCenter(from);
        addIfRightDownCorner(from);
        addIfDownLeftCorner(from);
        addIfUpRightCorner(from);
        addIfUpLeftCorner(from);
    }

    private void addIfRightDownCorner(Coordinate from) {
        if (from.isGungDownRightCorner()) {
            this.movements.add(Movement.UP_LEFT);
        }
    }

    private void addIfDownLeftCorner(Coordinate from) {
        if (from.isGungDownLeftCorner()) {
            this.movements.add(Movement.UP_RIGHT);
        }
    }

    private void addIfUpRightCorner(Coordinate from) {
        if (from.isGungUpRightCorner()) {
            this.movements.add(Movement.DOWN_LEFT);
        }
    }

    private void addIfUpLeftCorner(Coordinate from) {
        if (from.isGungUpLeftCorner()) {
            this.movements.add(Movement.DOWN_RIGHT);
        }
    }

    private void addIfCenter(Coordinate from) {
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

package movement;

import position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovePath {

    private final List<Movement> movements;

    public MovePath(Movement m) {
        this.movements = List.of(m);
    }

    public MovePath(Movement m1, Movement m2) {
        this.movements = (List.of(m1, m2));
    }

    public MovePath(Movement m1, Movement m2, Movement m3) {
        this.movements = (List.of(m1, m2, m3));
    }

    public MovePath(List<Movement> movements) {
        this.movements = movements;
    }

    public double calculateDistance() {
        int xDistance = 0;
        int yDistance = 0;
        for (Movement movement : movements) {
            xDistance += movement.x();
            yDistance += movement.y();
        }
        return Math.sqrt(Math.pow(Math.abs(xDistance), 2) + Math.pow(Math.abs(yDistance), 2));
    }

    public MovePaths getInternalMovements() {
        List<MovePath> moveActions = new ArrayList<>();
        List<Movement> buffer = new ArrayList<>();

        for (int i = 0; i < movements.size() - 1; i++) {
            buffer.add(movements.get(i));
            moveActions.add(new MovePath(new ArrayList<>(buffer)));
        }

        return new MovePaths(moveActions);
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public boolean canReachDestination(Position src, Position destination) {
        for (Movement movement : movements) {
            try {
                src = src.move(movement);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return src.equals(destination);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovePath movePath = (MovePath) o;
        return Objects.equals(movements, movePath.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(movements);
    }
}

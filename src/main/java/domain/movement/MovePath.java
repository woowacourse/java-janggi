package domain.movement;

import domain.position.Position;

import java.util.*;

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
        Set<MovePath> moveActions = new HashSet<>();
        List<Movement> buffer = new ArrayList<>();

        for (int i = 0; i < movements.size() - 1; i++) {
            buffer.add(movements.get(i));
            moveActions.add(new MovePath(new ArrayList<>(buffer)));
        }

        return new MovePaths(moveActions);
    }

    public boolean canReachDestination(Position src, Position destination) {
        for (Movement movement : movements) {
            if (src.canMove(movement)) {
                src = src.move(movement);
            }
        }
        return src.equals(destination);
    }

    public List<Movement> getMovements() {
        return List.copyOf(movements);
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

package domain.path;

import domain.board.Palace;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class LinearPathGenerator {
    public static List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        Direction direction = Direction.decideDirection(deltaX, deltaY);
        validateLinearMove(deltaX, deltaY, Palace.isPalacePath(departure, destination));

        return generateStraightPath(departure, destination, direction);
    }

    private static void validateLinearMove(int deltaX, int deltaY, boolean isPalacePath) {
        if (isPalacePath) {
            return;
        }

        if (isNotLinear(deltaX, deltaY)) {
            throw new IllegalArgumentException("직선 방향으로만 이동할 수 있습니다.");
        }
    }

    private static boolean isNotLinear(int deltaX, int deltaY) {
        return deltaX != 0 && deltaY != 0;
    }

    private static List<Position> generateStraightPath(Position departure, Position destination, Direction direction) {
        List<Position> paths = new ArrayList<>();

        Position current = departure;
        while (!current.equals(destination)) {
            current = current.move(direction.getDeltaX(), direction.getDeltaY());
            paths.add(current);
        }

        return paths;
    }
}

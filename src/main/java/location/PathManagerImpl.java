package location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathManagerImpl implements PathManager {
    private static final Map<Position, List<Position>> PALACE_DIAGONAL_MOVEMENT = Map.of(
            new Position(5, 2), List.of(new Position(4, 1), new Position(6, 1),
                    new Position(4, 3), new Position(6, 3)),
            new Position(6, 3), List.of(new Position(5, 2)),
            new Position(4, 3), List.of(new Position(5, 2)),
            new Position(6, 1), List.of(new Position(5, 2)),
            new Position(4, 1), List.of(new Position(5, 2)),
            new Position(5, 9), List.of(new Position(4, 8), new Position(6, 8),
                    new Position(4, 10), new Position(6, 10)),
            new Position(6, 10), List.of(new Position(5, 9)),
            new Position(4, 10), List.of(new Position(5, 9)),
            new Position(6, 8), List.of(new Position(5, 9)),
            new Position(4, 8), List.of(new Position(5, 9))
    );

    public void checkStraightMovement(Position from, Position to) {
        if (from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException("[ERROR] 직선 이동만 가능합니다.");
        }
    }

    public void checkOneMovement(Position from, Position to) {
        if (from.x() + 1 < to.x() || from.x() - 1 > to.x()
                || from.y() + 1 < to.y() || from.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 1칸만 이동 가능합니다.");
        }
    }

    public List<Position> calculateOneDirectionPaths(Position from, Position to) {
        Direction direction = Direction.findBy(from, to);
        List<Position> paths = new ArrayList<>();
        Position current = from.apply(direction);

        while (!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }

    public boolean isPalacePosition(Position destination) {
        if (destination.x() < 4 || 6 < destination.x()) {
            return false;
        }
        if (4 <= destination.y() && destination.y() <= 7) {
            return false;
        }
        return true;
    }

    public void checkValidOneDiagonalMovementInPalace(Position from, Position to) {
        List<Position> validDiagonalDestinations = PALACE_DIAGONAL_MOVEMENT.getOrDefault(from, Collections.emptyList());

        if (validDiagonalDestinations.isEmpty() || !validDiagonalDestinations.contains(to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
        }
    }

    public void checkValidTwoDiagonalMovementInPalace(Position from, Position to) {
        List<Position> validDiagonalPaths = PALACE_DIAGONAL_MOVEMENT.getOrDefault(from, Collections.emptyList());

        if (validDiagonalPaths.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
        }

        for (Position position : validDiagonalPaths) {
            List<Position> validDiagonalDestinations = PALACE_DIAGONAL_MOVEMENT.get(position);
            if (!validDiagonalDestinations.contains(to)) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
            }
        }
    }
}

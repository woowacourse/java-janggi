package domain.piece.strategy;

import domain.board.Palace;
import domain.board.Position;
import domain.path.Direction;

import java.util.ArrayList;
import java.util.List;

public abstract class LinearMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        Direction direction = decideLinearDirection(departure, destination);
        return generateStraightPath(departure, destination, direction);
    }

    private Direction decideLinearDirection(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateLinearMove(deltaX, deltaY, isPassByPalace(departure, destination));

        return Direction.decideDirection(deltaX, deltaY);
    }

    private boolean isPassByPalace(Position departure, Position destination) {
        int middlePositionColumn = (departure.column() + destination.column()) / 2;
        int middlePositionRow = (departure.row() + destination.row()) / 2;
        Position middlePosition = new Position(middlePositionColumn, middlePositionRow);

        return isInPalace(departure, destination) && Palace.isPalaceCenter(middlePosition);
    }

    private boolean isInPalace(Position departure, Position destination) {
        return Palace.isInPalace(departure) && Palace.isInPalace(destination);
    }

    private void validateLinearMove(int deltaX, int deltaY, boolean isPassByPalace) {
        if (isPassByPalace) {
            return;
        }

        if (isNotLinear(deltaX, deltaY)) {
            throw new IllegalArgumentException("직선 방향으로만 이동할 수 있습니다.");
        }
    }

    private boolean isNotLinear(int deltaX, int deltaY) {
        return deltaX != 0 && deltaY != 0;
    }

    private List<Position> generateStraightPath(Position departure, Position destination, Direction direction) {
        List<Position> paths = new ArrayList<>();

        Position current = departure;
        while (!current.equals(destination)) {
            current = current.move(direction.getDeltaX(), direction.getDeltaY());
            paths.add(current);
        }

        return paths;
    }
}

package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean isCanon() {
        return true;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {

        List<Position> path = new ArrayList<>();
        Position newPosition = startPosition;

        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) == 0) {
            newPosition = calculateNewPosition(startPosition.compareRow(targetPosition), newPosition, path, Move.BACK,
                    Move.FRONT);
        }
        if (startPosition.compareRow(targetPosition) == 0 && startPosition.compareColumn(targetPosition) != 0) {
            calculateNewPosition(startPosition.compareColumn(targetPosition), newPosition, path, Move.RIGHT,
                    Move.LEFT);
        }
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }

        return path;
    }

    private Position calculateNewPosition(int startPosition, Position newPosition,
                                          List<Position> path, Move backOrLeft,
                                          Move frontOrRight) {
        int count = startPosition;
        if (count < 0) {
            newPosition = addNewPositionOnPath(Math.abs(count), newPosition, backOrLeft, path);
        }
        if (count > 0) {
            newPosition = addNewPositionOnPath(count, newPosition, frontOrRight, path);
        }
        return newPosition;
    }

    private Position addNewPositionOnPath(int count, Position newPosition, Move backOrLeft, List<Position> path) {
        for (int i = 0; i < count - 1; i++) {
            newPosition = newPosition.movePosition(backOrLeft);
            path.add(newPosition);
        }
        return newPosition;
    }

}

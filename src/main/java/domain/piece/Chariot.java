package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {

        List<Position> path = new ArrayList<>();
        Position newPosition = startPosition;
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) == 0) {
            newPosition = calculateNewPosition(startPosition.compareRow(targetPosition), newPosition, Move.BACK, path,
                    Move.FRONT);
        }
        if (startPosition.compareRow(targetPosition) == 0 && startPosition.compareColumn(targetPosition) != 0) {
            calculateNewPosition(startPosition.compareColumn(targetPosition), newPosition, Move.RIGHT, path, Move.LEFT);
        }
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
        return path;
    }

    private Position calculateNewPosition(int startPosition, Position newPosition, Move back, List<Position> path,
                                          Move front) {
        int count = startPosition;
        if (count < 0) {
            newPosition = addNewPositionOnPath(Math.abs(count), newPosition, back, path);
        }
        if (count > 0) {
            newPosition = addNewPositionOnPath(count, newPosition, front, path);
        }
        return newPosition;
    }

    private Position addNewPositionOnPath(int count, Position newPosition, Move back, List<Position> path) {
        for (int i = 0; i < count - 1; i++) {
            newPosition = newPosition.movePosition(back);
            path.add(newPosition);
        }
        return newPosition;
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}
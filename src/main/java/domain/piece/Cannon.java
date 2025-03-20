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
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        //1. 세로가 다르고 가로가 같다.
        List<Position> path = new ArrayList<>();
        Position newPosition = startPosition;
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) == 0) {
            newPosition = calculateNewPostion(startPosition.compareRow(targetPosition), newPosition, path, Move.BACK,
                    Move.FRONT);
        }
        //2. 세로가 같고 가로가 다르다
        if (startPosition.compareRow(targetPosition) == 0 && startPosition.compareColumn(targetPosition) != 0) {
            calculateNewPostion(startPosition.compareColumn(targetPosition), newPosition, path, Move.RIGHT,
                    Move.LEFT);
        }
        //3. 세로가 같고, 가로가 같다
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }

        return path;
    }

    private Position calculateNewPostion(int startPosition, Position newPosition,
                                         List<Position> path, Move backOrLeft,
                                         Move frontOrRight) {
        int count = startPosition;
        if (count < 0) {
            for (int i = 0; i < Math.abs(count) - 1; i++) {
                newPosition = newPosition.movePosition(backOrLeft);
                path.add(newPosition);
            }
        }
        if (count > 0) {
            for (int i = 0; i < count - 1; i++) {
                newPosition = newPosition.movePosition(frontOrRight);
                path.add(newPosition);
            }
        }
        return newPosition;
    }

    @Override
    public boolean isCanon() {
        return true;
    }

}

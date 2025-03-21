package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {

        List<Position> path = new ArrayList<>();
        Position newPosition = startPosition;
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) == 0) {
            int count = startPosition.compareRow(targetPosition);
            if (count < 0) {
                for (int i = 0; i < Math.abs(count) - 1; i++) {
                    newPosition = newPosition.movePosition(Move.BACK);
                    path.add(newPosition);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count - 1; i++) {
                    newPosition = newPosition.movePosition(Move.FRONT);
                    path.add(newPosition);
                }
            }
        }
        if (startPosition.compareRow(targetPosition) == 0 && startPosition.compareColumn(targetPosition) != 0) {
            int count = startPosition.compareColumn(targetPosition);
            if (count < 0) {
                for (int i = 0; i < Math.abs(count) - 1; i++) {
                    newPosition = newPosition.movePosition(Move.RIGHT);
                    path.add(newPosition);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count - 1; i++) {
                    newPosition = newPosition.movePosition(Move.LEFT);
                    path.add(newPosition);
                }
            }
        }
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
        return path;
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}
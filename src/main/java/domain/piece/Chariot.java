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
    public boolean isCanon() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);

        if (rowDiff != 0 && columnDiff != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
        if (rowDiff < 0) {
            return determinePath(startPosition, Move.BACK, Math.abs(rowDiff));
        }
        if (rowDiff > 0) {
            return determinePath(startPosition, Move.FRONT, rowDiff);
        }
        if (columnDiff < 0) {
            return determinePath(startPosition, Move.RIGHT, Math.abs(columnDiff));
        }
        return determinePath(startPosition, Move.LEFT, columnDiff);
    }


    private List<Position> determinePath(Position startPosition, Move moveDirection, int steps) {
        List<Position> path = new ArrayList<>();
        Position currentPosition = startPosition;

        for (int i = 0; i < steps - 1; i++) {
            if (currentPosition.canMovePosition(moveDirection)) {
                currentPosition = currentPosition.movePosition(moveDirection);
                path.add(currentPosition);
            }
        }
        return path;
    }

}

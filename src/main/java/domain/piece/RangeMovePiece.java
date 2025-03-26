package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class RangeMovePiece extends Piece {

    public RangeMovePiece(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);

        validateSamePosition(rowDiff, columnDiff);
        return getPath(startPosition, rowDiff, columnDiff);
    }


    private List<Position> getPath(Position startPosition, int rowDiff, int columnDiff) {
        Move move;
        if (isVerticalMove(rowDiff)) {
            move = rowDiff < 0 ? Move.BACK : Move.FRONT;
            return determinePath(startPosition, move, rowDiff);
        }
        move = columnDiff < 0 ? Move.RIGHT : Move.LEFT;
        return determinePath(startPosition, move, columnDiff);
    }

    private void validateSamePosition(int rowDiff, int columnDiff) {
        if (rowDiff != 0 && columnDiff != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
    }

    private boolean isVerticalMove(int rowDiff) {
        return rowDiff != 0;
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

    @Override
    public boolean isCanon() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}

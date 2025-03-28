package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.List;

public abstract class RangeMovePiece extends Piece {

    public RangeMovePiece(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        if (isInPalaceDiagonalMove(startPosition, targetPosition)) {
            return decideDiagonalMove(startPosition, targetPosition).convertToPath(startPosition);
        }
        validateStraightMove(startPosition, targetPosition);
        Moves moves = decideMove(startPosition, targetPosition);
        return moves.convertToPath(startPosition);
    }

    private boolean isInPalaceDiagonalMove(Position startPosition, Position targetPosition) {
        return startPosition.isInPalace() && targetPosition.isInPalace()
                && Math.abs(startPosition.compareRow(targetPosition)) == Math.abs(
                startPosition.compareColumn(targetPosition));
    }

    private Moves decideDiagonalMove(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);
        if (rowDiff < 0 && columnDiff < 0) {
            return Moves.createStraightMove(Math.abs(rowDiff), Move.BACK_RIGHT);
        }
        if (rowDiff > 0 && columnDiff < 0) {
            return Moves.createStraightMove(Math.abs(rowDiff), Move.FRONT_RIGHT);
        }
        if (rowDiff > 0 && columnDiff > 0) {
            return Moves.createStraightMove(Math.abs(columnDiff), Move.FRONT_LEFT);
        }
        return Moves.createStraightMove(Math.abs(columnDiff), Move.BACK_LEFT);
    }

    private void validateStraightMove(Position startPosition, Position targetPosition) {
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
    }

    private Moves decideMove(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);
        if (rowDiff > 0) {
            return Moves.createStraightMove(rowDiff, Move.FRONT);
        }
        if (rowDiff < 0) {
            return Moves.createStraightMove(Math.abs(rowDiff), Move.BACK);
        }
        if (columnDiff < 0) {
            return Moves.createStraightMove(Math.abs(columnDiff), Move.RIGHT);
        }
        return Moves.createStraightMove(columnDiff, Move.LEFT);
    }
}

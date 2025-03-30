package domain.piece.movement;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;

public class StraightMoveStrategy implements MoveStrategy {

    @Override
    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        validateStraightMove(src, dest);
        int rowDiff = src.compareRow(dest);
        int columnDiff = src.compareColumn(dest);
        return List.of(calculateMoves(rowDiff, columnDiff));
    }

    private void validateStraightMove(Position src, Position dest) {
        if (Math.abs(src.compareRow(dest)) == Math.abs(src.compareColumn(dest))) {
            return;
        }
        if (src.compareRow(dest) == 0 || src.compareColumn(dest) == 0) {
            return;
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }

    private Moves calculateMoves(int rowDiff, int columnDiff) {
        if (isSameRow(rowDiff)) {
            return getMovesDifferentColumn(columnDiff);
        }
        if (isSameColumn(columnDiff)) {
            return getMovesDifferentRow(rowDiff);
        }
        return getMovesDifferentRowAndColumn(rowDiff, columnDiff);
    }

    private static Moves getMovesDifferentColumn(int columnDiff) {
        if (columnDiff < 0) {
            return Moves.createStraightMove(Math.abs(columnDiff), Move.RIGHT);
        }
        if (columnDiff > 0) {
            return Moves.createStraightMove(Math.abs(columnDiff), Move.LEFT);
        }
        return null;
    }

    private static Moves getMovesDifferentRow(int rowDiff) {
        if (rowDiff < 0) {
            return Moves.createStraightMove(Math.abs(rowDiff), Move.BACK);
        }
        if (rowDiff > 0) {
            return Moves.createStraightMove(rowDiff, Move.FRONT);
        }
        return null;
    }

    private static Moves getMovesDifferentRowAndColumn(int rowDiff, int columnDiff) {
        if (rowDiff > 0 && columnDiff < 0) {
            return Moves.createStraightMove(rowDiff, Move.FRONT_RIGHT);
        }
        if (rowDiff > 0 && columnDiff > 0) {
            return Moves.createStraightMove(rowDiff, Move.FRONT_LEFT);
        }
        if (rowDiff < 0 && columnDiff > 0) {
            return Moves.createStraightMove(Math.abs(rowDiff), Move.BACK_LEFT);
        }
        return Moves.createStraightMove(Math.abs(rowDiff), Move.BACK_RIGHT);
    }

    private boolean isSameRow(int rowDiff) {
        return rowDiff == 0;
    }

    private boolean isSameColumn(int columnDiff) {
        return columnDiff == 0;
    }
}

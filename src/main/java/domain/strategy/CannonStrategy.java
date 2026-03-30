package domain.strategy;

import domain.Position;
import domain.piece.Cannon;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    private static final List<Direction> straightDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        for (Direction direction : straightDirections) {
            addCannonCandidates(currentPosition, direction, board, candidates);
        }
        return candidates;
    }

    private void addCannonCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidatesPosition) {
        int nextRows = currentPosition.getRows() + direction.getRowOffset();
        int nextColumns = currentPosition.getColumns() + direction.getColOffset();

        while (isWithinBoard(nextRows, nextColumns)) {
            Position nextPosition = new Position(nextRows, nextColumns);
            if (!board.isBlank(nextPosition)) {
                checkBridgeAndCollect(nextPosition, direction, board, candidatesPosition);
                return;
            }
            nextRows += direction.getRowOffset();
            nextColumns += direction.getColOffset();
        }
    }

    private void checkBridgeAndCollect(Position nextPosition, Direction direction, PieceProvider board, List<Position> candidatesPosition) {
        if (board.getPiece(nextPosition) instanceof Cannon) return;
        int nextRow = nextPosition.getRows() + direction.getRowOffset();
        int nextColumn = nextPosition.getColumns() + direction.getColOffset();

        while (isWithinBoard(nextRow, nextColumn)) {
            if (addCandidateAndCheckPiece(new Position(nextRow, nextColumn), board, candidatesPosition)) return;
            nextRow += direction.getRowOffset();
            nextColumn += direction.getColOffset();
        }

    }

    private boolean addCandidateAndCheckPiece(Position targetPosition, PieceProvider board, List<Position> candidatesPosition) {
        if (board.isBlank(targetPosition)) {
            candidatesPosition.add(targetPosition);
            return false;
        }
        if (!(board.getPiece(targetPosition) instanceof Cannon)) {
            candidatesPosition.add(targetPosition);
        }
        return true;
    }


    private boolean isWithinBoard(int row, int column) {
        return row >= 0 && row < 10 && column >= 0 && column < 9;
    }
}

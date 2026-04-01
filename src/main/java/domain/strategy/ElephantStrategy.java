package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    private static final List<Direction> straightDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Team team = board.getPiece(currentPosition).getTeam();
        for (Direction straight : straightDirections) {
            processStraight(currentPosition, straight, board, candidates, team);
        }
        return candidates;
    }

    private void processStraight(Position current, Direction straight, PieceProvider board, List<Position> candidates, Team team) {
        int straightRow = current.getRows() + straight.getRowOffset();
        int straightCol = current.getColumns() + straight.getColOffset();

        if (isBlocked(straightRow, straightCol, board)) return;

        Position straightMyeokPosition = new Position(straightRow, straightCol);
        for (Direction diagonal : getDiagonalsFor(straight)) {
            processDiagonal(straightMyeokPosition, diagonal, board, candidates, team);
        }
    }

    private void processDiagonal(Position straightMyeokPosition, Direction diagonal, PieceProvider board, List<Position> candidates, Team team) {
        int diagonalRow = straightMyeokPosition.getRows() + diagonal.getRowOffset();
        int diagonalColumn = straightMyeokPosition.getColumns() + diagonal.getColOffset();

        if (isBlocked(diagonalRow, diagonalColumn, board)) return;

        int targetRow = diagonalRow + diagonal.getRowOffset();
        int targetCol = diagonalColumn + diagonal.getColOffset();

        if (CollisionValidator.isWithinBoard(targetRow, targetCol)) {
            Position targetPositon = new Position(targetRow, targetCol);
            addValidateCandidates(targetPositon, board, candidates, team);
        }
    }

    private void addValidateCandidates(Position target, PieceProvider board, List<Position> candidates, Team team) {
        if (CollisionValidator.canMoveToTarget(target, board, team)) {
            candidates.add(target);
        }
    }

    private boolean isBlocked(int row, int column, PieceProvider board) {
        if (!CollisionValidator.isWithinBoard(row, column)) {
            return true;
        }
        return !board.isBlank(new Position(row, column));
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}

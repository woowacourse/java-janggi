package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final List<Direction> straightDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        for (Direction direction : straightDirections) {
            checkMyeokPosition(currentPosition, direction, board, candidates);
        }
        return candidates;
    }

    private void checkMyeokPosition(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidates) {
        int myeokRow = currentPosition.getRows() + direction.getRowOffset();
        int myeokColumn = currentPosition.getColumns() + direction.getColOffset();

        if (CollisionValidator.isWithinBoard(myeokRow, myeokColumn)) {
            Position myeokPosition = new Position(myeokRow, myeokColumn);
            validateMyeok(currentPosition,myeokPosition, direction, candidates,board);
        }
    }

    private void validateMyeok(Position currentPosition, Position myeokPosition, Direction straight, List<Position> candidates, PieceProvider board) {
        if (board.isBlank(myeokPosition)) {
            collectHorseTargets(currentPosition, straight, board, candidates);
        }
    }

    private void collectHorseTargets(Position currentPosition, Direction straight, PieceProvider board, List<Position> candidates) {
        Team team = board.getPiece(currentPosition).getTeam();
        for (Direction diag : getDiagonalsFor(straight)) {
            int targetRow = currentPosition.getRows() + straight.getRowOffset() + diag.getRowOffset();
            int targetColumn = currentPosition.getColumns() + straight.getColOffset() + diag.getColOffset();
            addValidatedCandidates(targetRow, targetColumn, board, candidates, team);
        }
    }

    private void addValidatedCandidates(int row, int column, PieceProvider board, List<Position> candidates, Team team) {
        if (CollisionValidator.isWithinBoard(row, column)) {
            Position target = new Position(row, column);
            if (CollisionValidator.canMoveToTarget(target, board, team)) {
                candidates.add(target);
            }
        }
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}

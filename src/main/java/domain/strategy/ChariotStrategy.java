package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class ChariotStrategy implements MoveStrategy {

    private static final List<Direction> straightDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        for (Direction direction : straightDirections) {
            addPathCandidates(currentPosition, direction, board, candidates);
        }
        return candidates;
    }

    private void addPathCandidates(Position currentPosition, Direction direction, PieceProvider board, List<Position> candidates) {
        int nextRows = currentPosition.getRows() + direction.getRowOffset();
        int nextColumns = currentPosition.getColumns() + direction.getColOffset();
        Team team = board.getPiece(currentPosition).getTeam();

        while (CollisionValidator.isWithinBoard(nextRows, nextColumns)) {
            Position nextPosition = new Position(nextRows, nextColumns);
            boolean canContinue = addAndCheckContinue(nextPosition, board, candidates, team);
            if (!canContinue) {
                break;
            }
            nextRows += direction.getRowOffset();
            nextColumns += direction.getColOffset();
        }
    }

    private boolean addAndCheckContinue(Position nextPosition, PieceProvider board, List<Position> candidates, Team team) {
        if (CollisionValidator.canMoveToTarget(nextPosition, board, team)) {
            candidates.add(nextPosition);
        }
        return board.isBlank(nextPosition);
    }
}

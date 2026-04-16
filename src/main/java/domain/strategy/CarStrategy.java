package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class CarStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        for (Direction direction : getDirections()) {
            addPathCandidates(from, direction, board, team, candidates);
        }
        return candidates;
    }

    private void addPathCandidates(Position from, Direction direction, PieceProvider board, Team team,
                                   List<Position> candidatePositions) {
        int rowOffset = direction.getRowOffset(team);
        int colOffset = direction.getColOffset(team);
        Position next = from;

        while (next.canMoveNext(rowOffset, colOffset)) {
            next = next.next(rowOffset, colOffset);

            if (board.getPiece(next).isBlank()) {
                candidatePositions.add(next);
                continue;
            }

            if (board.getPiece(next).isOtherTeam(team)) {
                candidatePositions.add(next);
            }

            break;
        }
    }
}

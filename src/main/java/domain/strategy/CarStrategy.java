package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import view.OutputView;

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
        Position next = from.next(direction.getRowOffset(team), direction.getColOffset(team));

        while (!next.isInvalid()) {
            if (board.getPiece(next).isBlank()) {
                candidatePositions.add(next);
                next = next.next(direction.getRowOffset(team), direction.getColOffset(team));
                continue;
            }
            if (board.getPiece(next).isOtherTeam(team)) {
                candidatePositions.add(next);
            }
            break;
        }
    }
}

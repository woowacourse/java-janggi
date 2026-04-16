package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CannonStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        return getDirections().stream()
                .flatMap(direction -> addCannonCandidates(from, team, direction, board).stream())
                .collect(Collectors.toList());
    }

    private List<Position> addCannonCandidates(Position from, Team team, Direction direction, PieceProvider board) {
        Position myeok = findObstacle(from,team, direction, board);

        if (board.getPiece(myeok).isCannon()) {
            return Collections.emptyList();
        }

        return collectTargets(myeok, team, direction, board);
    }

    private Position findObstacle(Position from, Team team, Direction direction, PieceProvider board) {
        int rowOffset = direction.getRowOffset(team);
        int colOffset = direction.getColOffset(team);
        Position obstacle = from;

        while (obstacle.canMoveNext(rowOffset, colOffset)) {
            obstacle = obstacle.next(rowOffset, colOffset);

            if (!board.getPiece(obstacle).isBlank()) {
                return obstacle;
            }
        }

        return obstacle;
    }

    private List<Position> collectTargets(Position obstacle, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        int rowOffset = direction.getRowOffset(team);
        int colOffset = direction.getColOffset(team);
        Position target = obstacle;

        while (target.canMoveNext(rowOffset, colOffset)) {
            target = target.next(rowOffset, colOffset);

            if (board.getPiece(target).isBlank()) {
                candidates.add(target);
                continue;
            }

            if (board.getPiece(target).isOtherTeam(team) && !board.getPiece(target).isCannon()) {
                candidates.add(target);
            }

            break;
        }

        return candidates;
    }
}

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

        if (board.getPiece(myeok).isOtherTeam(team) || board.getPiece(myeok).isCannon()) {
            return Collections.emptyList();
        }

        return collectTargets(myeok, team, direction, board);
    }

    private Position findObstacle(Position from, Team team, Direction direction, PieceProvider board) {
        Position obstacle = from.next(direction.getRowOffset(team), direction.getColOffset(team));
        while (!obstacle.isInvalid() && board.isBlank(obstacle)) {
            obstacle = obstacle.next(direction.getRowOffset(team), direction.getColOffset(team));
        }
        return obstacle;
    }

    private List<Position> collectTargets(Position obstacle, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position target = obstacle.next(direction.getRowOffset(team), direction.getColOffset(team));

        while (!target.isInvalid()) {
            if (board.isBlank(target)) {
                candidates.add(target);
                target = target.next(direction.getRowOffset(team), direction.getColOffset(team));
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

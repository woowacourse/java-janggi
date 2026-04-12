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
        Position bridge = findFirstPiece(from,team, direction, board);
        boolean isCannon = board.isCannon(bridge);

        if (bridge.isInvalid() || isCannon) {
            return Collections.emptyList();
        }

        return collectTargets(bridge, team, direction, board);
    }

    private Position findFirstPiece(Position from, Team team, Direction direction, PieceProvider board) {
        Position nextPosition = getNext(from, team, direction);
        while (!nextPosition.isInvalid() && board.isBlank(nextPosition)) {
            nextPosition = getNext(nextPosition, team, direction);
        }
        return nextPosition;
    }

    private Position getNext(Position from, Team team, Direction direction) {
        int nextRows = from.row() + direction.getRowOffset(team);
        int nextColumns = from.col() + direction.getColOffset(team);
        return new Position(nextRows, nextColumns);
    }

    private List<Position> collectTargets(Position bridge, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position target = getNext(bridge, team, direction);

        while (!target.isInvalid()) {
            if (board.isBlank(target)) {
                candidates.add(target);
                target = getNext(target, team, direction);
                continue;
            }

            if (board.getPiece(target).getTeam() != team && !board.isCannon(target)) {
                candidates.add(target);
            }

            break;
        }
        return candidates;
    }
}

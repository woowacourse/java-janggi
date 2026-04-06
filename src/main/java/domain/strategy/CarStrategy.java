package domain.strategy;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.Team;
import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CarStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {

        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        return getDirections().stream()
                .flatMap(direction -> addPathCandidates(from, team, direction, board).stream())
                .collect(Collectors.toList());
    }

    private List<Position> addPathCandidates(Position from, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position next = from;

        while (true) {
            int nextRows = next.row() + direction.getRowOffset(team);
            int nextColumns = next.col() + direction.getColOffset(team);

            Position bridge = new Position(nextRows, nextColumns);

            if (bridge.isInvalid()) {
                break;
            }

            next = new Position(nextRows, nextColumns);
            if (board.isBlank(next)) {
                candidates.add(next);
                continue;
            }
            candidates.add(next);
            break;
        }

        return candidates;
    }
}

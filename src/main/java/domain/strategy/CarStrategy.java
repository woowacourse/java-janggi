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

        int nextRow = from.row() + direction.getRowOffset(team);
        int nextCol = from.col() + direction.getColOffset(team);
        Position next = new Position(nextRow, nextCol);

        while (!next.isInvalid()) {
            candidates.add(next);

            if (!board.isBlank(next)) {
                break;
            }

            nextRow = next.row() + direction.getRowOffset(team);
            nextCol = next.col() + direction.getColOffset(team);
            next = new Position(nextRow, nextCol);
        }

        return candidates;
    }
}

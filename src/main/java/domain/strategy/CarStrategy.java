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
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {

        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        return Arrays.stream(Direction.values()) // 모든 방향 혹은 특정 방향 배열
                .flatMap(direction -> addPathCandidates(from, team, direction, board).stream())
                .collect(Collectors.toList());
    }

    private List<Position> addPathCandidates(Position from, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position next = from;

        while (true) {
            int nextRows = next.row() + direction.getRowOffset(team);
            int nextColumns = next.col() + direction.getColOffset(team);

            if (nextRows < 0 || nextRows >= BOARD_ROWS.getIndex() || nextColumns < 0
                    || nextColumns >= BOARD_COLUMNS.getIndex()) {
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

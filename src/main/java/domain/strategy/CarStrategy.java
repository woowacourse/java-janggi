package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.board.PieceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarStrategy implements Strategy {

    @Override
    public List<Direction> getDirections() {
        return List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        return getDirections().stream()
                .flatMap(direction -> addPathCandidates(from, team, direction, board).stream())
                .collect(Collectors.toList());
    }

    private List<Position> addPathCandidates(Position from, Team team, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position next = move(from, direction, team);

        while (!next.isInvalid()) {
            candidates.add(next);
            next = nextPosition(next, direction, team, board);
        }
        return candidates;
    }

    private Position nextPosition(Position current, Direction direction, Team team, PieceProvider board) {
        if (!board.isBlank(current)) {
            return current; // isInvalid()가 true인 sentinel 역할 → 루프 종료 필요
        }
        return move(current, direction, team);
    }

    private Position move(Position pos, Direction direction, Team team) {
        int nextRow = pos.row() + direction.getRowOffset(team);
        int nextCol = pos.col() + direction.getColOffset(team);
        return new Position(nextRow, nextCol);
    }
}

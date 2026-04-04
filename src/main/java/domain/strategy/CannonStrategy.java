package domain.strategy;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CannonStrategy implements Strategy {

    @Override
    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        return Arrays.stream(straightDirections)
                .flatMap(direction -> addCannonCandidates(from, direction, board).stream())
                .collect(Collectors.toList());
    }

    private List<Position> addCannonCandidates(Position from, Direction direction, PieceProvider board) {
        Position bridge = findFirstPiece(from, direction, board);
        boolean isCannon = board.isCannon(bridge);

        if (!isWithinBoard(bridge) || isCannon) {
            return Collections.emptyList();
        }

        return collectTargets(bridge, direction, board);
    }

    private Position findFirstPiece(Position from, Direction direction, PieceProvider board) {
        Position nextPosition = getNext(from, direction);
        while (isWithinBoard(nextPosition) && board.isBlank(nextPosition)) {
            nextPosition = getNext(nextPosition, direction);
        }
        return nextPosition;
    }

    private Position getNext(Position from, Direction direction) {
        int nextRows = from.row() + direction.getRowOffset();
        int nextColumns = from.col() + direction.getColOffset();
        return new Position(nextRows, nextColumns);
    }

    private List<Position> collectTargets(Position bridge, Direction direction, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Position target = getNext(bridge, direction);

        while (isWithinBoard(target) && board.isBlank(target)) {
            candidates.add(target);
            target = getNext(target, direction);
        }

        boolean isCannon = board.isCannon(target);
        if (isWithinBoard(target) && !isCannon) {
            candidates.add(target);
        }

        return candidates;
    }

    private boolean isWithinBoard(Position from) {
        return from.row() >= 0 && from.row() < BOARD_ROWS.getIndex() &&
                from.col() >= 0 && from.col() < BOARD_COLUMNS.getIndex();
    }
}

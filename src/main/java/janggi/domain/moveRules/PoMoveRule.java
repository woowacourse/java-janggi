package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PoMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position position, Team team, Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>();
        List<Direction> directions = getPoDirections();
        for (Direction direction : directions) {
            Position bridge = findBridge(position, direction, state);
            if (bridge != null) {
                collectPositions(bridge, direction, team, state, availablePositions);
            }
        }
        return availablePositions;
    }

    private Position findBridge(Position currentPosition, Direction direction, Map<Position, Piece> state) {
        if (currentPosition.cannotMoveTo(direction)) {
            return null;
        }

        // TODO: 보드판 범위를 벗어나는 Position으로 move 시에 오류 해결
        Position next = currentPosition.move(direction);
        Piece piece = state.get(next);

        if (piece == null) {
            return findBridge(next, direction, state);
        }
        if (piece.isPo()) {
            return null;
        }
        return next;
    }

    private void collectPositions(Position currentPosition, Direction direction, Team team, Map<Position, Piece> state,
                                  List<Position> result) {
        if (currentPosition.cannotMoveTo(direction)) {
            return;
        }
        Position next = currentPosition.move(direction);
        Piece piece = state.get(next);

        addIfValid(piece, next, team, result);

        if (piece == null) {
            collectPositions(next, direction, team, state, result);
        }
    }

    private void addIfValid(Piece piece, Position next, Team team, List<Position> result) {
        if (piece == null) {
            result.add(next);
            return;
        }
        if (!piece.isAlly(team) && !piece.isPo()) {
            result.add(next);
        }
    }

    private static List<Direction> getPoDirections() {
        return Arrays.stream(Direction.values())
                .filter(Direction::isStraight)
                .toList();
    }
}

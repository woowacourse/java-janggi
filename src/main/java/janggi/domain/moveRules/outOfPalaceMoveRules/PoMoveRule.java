package janggi.domain.moveRules.outOfPalaceMoveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PoMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position position,
                                                      Team team,
                                                      Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : Direction.getStraightDirections()) {
            Position bridge = findBridge(position, direction, state);
            if (bridge != null) {
                List<Position> destinations = findDestinationsAfterBridge(bridge, direction, team, state);
                availablePositions.addAll(destinations);
            }
        }
        return availablePositions;
    }

    private Position findBridge(Position start, Direction direction, Map<Position, Piece> state) {
        Position current = start;
        while (!current.cannotMoveTo(direction)) {
            current = current.move(direction);
            Piece piece = state.get(current);

            if (piece != null) {
                return piece.isPo() ? null : current;
            }
        }
        return null;
    }

    private List<Position> findDestinationsAfterBridge(Position bridge,
                                                       Direction direction,
                                                       Team team,
                                                       Map<Position, Piece> state) {
        List<Position> destinations = new ArrayList<>();
        Position current = bridge;

        while (!current.cannotMoveTo(direction)) {
            current = current.move(direction);
            Piece piece = state.get(current);

            if (piece == null) {
                destinations.add(current);
                continue;
            }

            if (piece.isEnemy(team) && !piece.isPo()) {
                destinations.add(current);
            }
            break;
        }
        return destinations;
    }
}

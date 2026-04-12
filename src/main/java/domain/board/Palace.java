package domain.board;

import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Palace {
    private final Set<Position> positions = Set.of(
            new Position(9, 3), new Position(9, 4), new Position(9, 5),
            new Position(8, 3), new Position(8, 4), new Position(8, 5),
            new Position(7, 3), new Position(7, 4), new Position(7, 5),
            new Position(2, 3), new Position(2, 4), new Position(2, 5),
            new Position(1, 3), new Position(1, 4), new Position(1, 5),
            new Position(0, 3), new Position(0, 4), new Position(0, 5)
    );
    private final Map<Position, Set<Position>> connections = initializeConnections();
    private final Map<Position, Set<Position>> doubleConnections = initializeDoubleConnections();

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public boolean isSingleStepDiagonalConnection(Position departure, Position destination) {
        return isConnected(departure, destination)
                && departure.isSingleStepDiagonalTo(destination);
    }

    public boolean isSlidingDiagonalConnection(Position departure, Position destination) {
        return isSlidingConnected(departure, destination)
                && isSlidingDiagonalDistance(departure, destination);
    }

    private static boolean isSlidingDiagonalDistance(Position departure, Position destination) {
        return departure.isSingleStepDiagonalTo(destination)
                || departure.isDoubleStepDiagonalTo(destination);
    }

    private boolean isSlidingConnected(Position departure, Position destination) {
        return isConnected(departure, destination) || isDoubleConnected(departure,destination);
    }

    public boolean isConnected(Position departure, Position destination) {
        return connections.getOrDefault(departure, Set.of()).contains(destination);
    }

    private boolean isDoubleConnected(Position departure, Position destination) {
        return doubleConnections.getOrDefault(departure, Set.of()).contains(destination);
    }

    private Map<Position, Set<Position>> initializeDoubleConnections() {
        Map<Position, Set<Position>> palaceConnections = new HashMap<>();
        connectBidirectional(palaceConnections, 9, 3, 7, 5);
        connectBidirectional(palaceConnections, 9, 5, 7, 3);
        connectBidirectional(palaceConnections, 0, 3, 2, 5);
        connectBidirectional(palaceConnections, 0, 5, 2, 3);
        return palaceConnections;
    }

    private Map<Position, Set<Position>> initializeConnections() {
        Map<Position, Set<Position>> palaceConnections = new HashMap<>();

        connectBidirectional(palaceConnections, 9, 3, 9, 4);
        connectBidirectional(palaceConnections, 9, 4, 9, 5);
        connectBidirectional(palaceConnections, 8, 3, 8, 4);
        connectBidirectional(palaceConnections, 8, 4, 8, 5);
        connectBidirectional(palaceConnections, 7, 3, 7, 4);
        connectBidirectional(palaceConnections, 7, 4, 7, 5);
        connectBidirectional(palaceConnections, 9, 3, 8, 3);
        connectBidirectional(palaceConnections, 8, 3, 7, 3);
        connectBidirectional(palaceConnections, 9, 4, 8, 4);
        connectBidirectional(palaceConnections, 8, 4, 7, 4);
        connectBidirectional(palaceConnections, 9, 5, 8, 5);
        connectBidirectional(palaceConnections, 8, 5, 7, 5);
        connectBidirectional(palaceConnections, 9, 3, 8, 4);
        connectBidirectional(palaceConnections, 8, 4, 7, 5);
        connectBidirectional(palaceConnections, 9, 5, 8, 4);
        connectBidirectional(palaceConnections, 8, 4, 7, 3);

        connectBidirectional(palaceConnections, 2, 3, 2, 4);
        connectBidirectional(palaceConnections, 2, 4, 2, 5);
        connectBidirectional(palaceConnections, 1, 3, 1, 4);
        connectBidirectional(palaceConnections, 1, 4, 1, 5);
        connectBidirectional(palaceConnections, 0, 3, 0, 4);
        connectBidirectional(palaceConnections, 0, 4, 0, 5);
        connectBidirectional(palaceConnections, 2, 3, 1, 3);
        connectBidirectional(palaceConnections, 1, 3, 0, 3);
        connectBidirectional(palaceConnections, 2, 4, 1, 4);
        connectBidirectional(palaceConnections, 1, 4, 0, 4);
        connectBidirectional(palaceConnections, 2, 5, 1, 5);
        connectBidirectional(palaceConnections, 1, 5, 0, 5);
        connectBidirectional(palaceConnections, 2, 3, 1, 4);
        connectBidirectional(palaceConnections, 1, 4, 0, 5);
        connectBidirectional(palaceConnections, 2, 5, 1, 4);
        connectBidirectional(palaceConnections, 1, 4, 0, 3);

        return palaceConnections;
    }

    private void connectBidirectional(Map<Position, Set<Position>> palaceConnections,
                                      int departureRow,
                                      int departureColumn,
                                      int destinationRow,
                                      int destinationColumn) {
        Position departure = new Position(departureRow, departureColumn);
        Position destination = new Position(destinationRow, destinationColumn);
        palaceConnections.merge(departure, Set.of(destination), this::merge);
        palaceConnections.merge(destination, Set.of(departure), this::merge);
    }

    private Set<Position> merge(Set<Position> positions, Set<Position> otherPositions) {
        Set<Position> merged = new java.util.HashSet<>(positions);
        merged.addAll(otherPositions);
        return Set.copyOf(merged);
    }
}

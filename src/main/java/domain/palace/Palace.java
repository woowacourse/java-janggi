package domain.palace;

import domain.board.Position;
import domain.piece.TeamColor;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Palace {
    private static final Position CHO_CENTER = Position.of(8, 4);
    private static final Set<Position> CHO_CORNERS = Set.of(
            Position.of(7, 3),
            Position.of(7, 5),
            Position.of(9, 3),
            Position.of(9, 5)
    );
    private static final Position HAN_CENTER = Position.of(1, 4);
    private static final Set<Position> HAN_CORNERS = Set.of(
            Position.of(0, 3),
            Position.of(0, 5),
            Position.of(2, 3),
            Position.of(2, 5)
    );

    private final Map<Position, Set<Position>> connections;
    private final Position center;
    private final Set<Position> corners;

    private Palace(Map<Position, Set<Position>> connections, Position center, Set<Position> corners) {
        this.connections = connections;
        this.center = center;
        this.corners = corners;
    }

    public static Palace of(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return new Palace(Map.of(
                    Position.of(7, 3), Set.of(Position.of(7, 4), Position.of(8, 3), Position.of(8, 4)),
                    Position.of(7, 4), Set.of(Position.of(7, 3), Position.of(7, 5), Position.of(8, 4)),
                    Position.of(7, 5), Set.of(Position.of(7, 4), Position.of(8, 4), Position.of(8, 5)),
                    Position.of(8, 3), Set.of(Position.of(7, 3), Position.of(8, 4), Position.of(9, 3)),
                    Position.of(8, 4), Set.of(
                            Position.of(7, 3), Position.of(7, 4), Position.of(7, 5),
                            Position.of(8, 3), Position.of(8, 5),
                            Position.of(9, 3), Position.of(9, 4), Position.of(9, 5)
                    ),
                    Position.of(8, 5), Set.of(Position.of(7, 5), Position.of(8, 4), Position.of(9, 5)),
                    Position.of(9, 3), Set.of(Position.of(8, 3), Position.of(8, 4), Position.of(9, 4)),
                    Position.of(9, 4), Set.of(Position.of(9, 3), Position.of(9, 5), Position.of(8, 4)),
                    Position.of(9, 5), Set.of(Position.of(8, 4), Position.of(8, 5), Position.of(9, 4))
            ), CHO_CENTER, CHO_CORNERS);
        }
        return new Palace(Map.of(
                Position.of(0, 3), Set.of(Position.of(0, 4), Position.of(1, 3), Position.of(1, 4)),
                Position.of(0, 4), Set.of(Position.of(0, 3), Position.of(0, 5), Position.of(1, 4)),
                Position.of(0, 5), Set.of(Position.of(0, 4), Position.of(1, 4), Position.of(1, 5)),
                Position.of(1, 3), Set.of(Position.of(0, 3), Position.of(1, 4), Position.of(2, 3)),
                Position.of(1, 4), Set.of(
                        Position.of(0, 3), Position.of(0, 4), Position.of(0, 5),
                        Position.of(1, 3), Position.of(1, 5),
                        Position.of(2, 3), Position.of(2, 4), Position.of(2, 5)
                ),
                Position.of(1, 5), Set.of(Position.of(0, 5), Position.of(1, 4), Position.of(2, 5)),
                Position.of(2, 3), Set.of(Position.of(1, 3), Position.of(1, 4), Position.of(2, 4)),
                Position.of(2, 4), Set.of(Position.of(2, 3), Position.of(2, 5), Position.of(1, 4)),
                Position.of(2, 5), Set.of(Position.of(1, 4), Position.of(1, 5), Position.of(2, 4))
        ), HAN_CENTER, HAN_CORNERS);
    }

    public boolean contains(Position position) {
        return connections.containsKey(position);
    }

    public Set<Position> connectedPositions(Position position) {
        return connections.getOrDefault(position, Set.of());
    }

    public Position center() {
        return center;
    }

    public Set<Position> corners() {
        return corners;
    }

    public boolean isCenter(Position position) {
        return center.equals(position);
    }

    public boolean isCorner(Position position) {
        return corners.contains(position);
    }

    public Optional<Position> oppositeCorner(Position corner) {
        if (!isCorner(corner)) {
            return Optional.empty();
        }

        final int oppositeRow = center.row() - (corner.row() - center.row());
        final int oppositeColumn = center.column() - (corner.column() - center.column());
        return Optional.of(Position.of(oppositeRow, oppositeColumn));
    }

    public boolean isForwardForTeam(Position currentPosition, Position targetPosition, TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return targetPosition.row() < currentPosition.row();
        }
        return targetPosition.row() > currentPosition.row();
    }

    public boolean isBackwardForTeam(Position currentPosition, Position targetPosition, TeamColor teamColor) {
        return !isForwardForTeam(currentPosition, targetPosition, teamColor);
    }
}

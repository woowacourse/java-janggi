package domain.board;

import domain.piece.TeamColor;
import java.util.Map;
import java.util.Set;

public class Palace {
    private final Map<Position, Set<Position>> connections;

    private Palace(Map<Position, Set<Position>> connections) {
        this.connections = connections;
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
            ));
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
        ));
    }

    public boolean contains(Position position) {
        return connections.containsKey(position);
    }

    public Set<Position> connectedPositions(Position position) {
        return connections.getOrDefault(position, Set.of());
    }
}

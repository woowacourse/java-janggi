package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Route;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final Map<Team, Integer> direction = Map.of(RED, -1, BLUE, 1);

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public List<Route> calculateRoutes() {
        final List<Position> destinations = new ArrayList<>();

        final List<RawPosition> rawPositions = calculateDestinations();
        for (RawPosition rawPosition : rawPositions) {
            try {
                destinations.add(new Position(rawPosition.x(), rawPosition.y()));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        return destinations.stream()
                .map(destination -> new Route(new HashSet<>(), destination))
                .toList();
    }

    @Override
    protected List<RawPosition> calculateDestinations() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x(), position.y() + direction.get(team)));
    }
}

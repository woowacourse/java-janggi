package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Team;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Soldier extends Piece {

    private static final Map<Team, Integer> direction = Map.of(RED, -1, BLUE, 1);

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> calculateRoute() {
        return new HashSet<>();
    }

    @Override
    protected List<RawPosition> calculateDestinations() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x(), position.y() + direction.get(team)));
    }
}

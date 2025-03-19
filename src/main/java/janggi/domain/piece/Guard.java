package janggi.domain.piece;

import janggi.domain.Team;
import java.util.List;
import java.util.Set;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> calculateRoute() {
        return Set.of();
    }

    @Override
    protected List<RawPosition> calculateDestinations() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x(), position.y() + 1),
                new RawPosition(position.x(), position.y() - 1));
    }
}

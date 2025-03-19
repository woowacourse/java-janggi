package janggi.domain.piece;

import janggi.domain.RawRoute;
import janggi.domain.Team;
import java.util.List;
import java.util.Set;

public class General extends Piece {

    public General(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x(), position.y() + 1))),
                new RawRoute(List.of(new RawPosition(position.x(), position.y() - 1)))
        );
    }
}

package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicy;
import janggi.domain.routePolicy.RoutePolicyForNormal;
import java.util.List;
import java.util.Set;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team, new RoutePolicyForNormal());
        this.pieceType = PieceType.GUARD;
    }

    public RoutePolicy getMovePolicy() {
        return movePolicy;
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

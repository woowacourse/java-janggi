package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForNormal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Soldier extends Piece {

    private static final int BLUE_DIRECTION = 1;
    private static final int RED_DIRECTION = -BLUE_DIRECTION;
    private static final Map<Team, Integer> DIRECTION = Map.of(
            RED, RED_DIRECTION,
            BLUE, BLUE_DIRECTION
    );

    public Soldier(final Position position, final Team team) {
        super(team, position, PieceType.SOLDIER, new RoutePolicyForNormal());
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(List.of(position.right())),
                new RawRoute(List.of(position.left())),
                new RawRoute(List.of(position.upOrDown(DIRECTION.get(team))))
        );
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of(
                new RawRoute(List.of(position.upOrDownRightDiagonal(DIRECTION.get(team)))),
                new RawRoute(List.of(position.upOrDownLeftDiagonal(DIRECTION.get(team))))
        );
    }
}

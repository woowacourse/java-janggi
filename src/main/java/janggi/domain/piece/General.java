package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForPalaceConstraint;
import java.util.List;
import java.util.Set;

public class General extends Piece {

    public General(final Position position, final Team team) {
        super(team, position, PieceType.GENERAL, new RoutePolicyForPalaceConstraint());
        this.pieceType = PieceType.GENERAL;
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(List.of(position.right())),
                new RawRoute(List.of(position.left())),
                new RawRoute(List.of(position.up())),
                new RawRoute(List.of(position.down()))
        );
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of(
                new RawRoute(List.of(position.upRightDiagonal())),
                new RawRoute(List.of(position.upLeftDiagonal())),
                new RawRoute(List.of(position.downLeftDiagonal())),
                new RawRoute(List.of(position.downRightDiagonal()))
        );
    }
}

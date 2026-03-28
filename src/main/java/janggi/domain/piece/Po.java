package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.PoCollisionDetector;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import java.util.List;

public class Po extends Piece {

    private static final RouteProvider ROUTE_PROVIDER = StraightRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = PoCollisionDetector.getInstance();

    public Po(Side side) {
        super(PieceType.PO, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return ROUTE_PROVIDER.calculateRoute(pieceType, from, to);
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}


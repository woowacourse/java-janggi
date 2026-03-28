package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.GungSeongRouteProvider;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;

public class Gung extends Piece {

    private static final RouteProvider ROUTE_PROVIDER = GungSeongRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Gung(Side side) {
        super(PieceType.GUNG, side);
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

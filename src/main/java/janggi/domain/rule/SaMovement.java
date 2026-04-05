package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.GungSeongRouteProvider;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public class SaMovement implements Movement {

    private static final SaMovement INSTANCE = new SaMovement();

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector;

    private SaMovement() {
        this.routeProvider = GungSeongRouteProvider.getInstance();
        this.collisionDetector = DefaultCollisionDetector.getInstance();
    }

    public static SaMovement getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        return routeProvider.calculateRoute(from, to);
    }

    @Override
    public void detectCollision(Side side, List<Piece> piecesOnPath) {
        collisionDetector.check(side, piecesOnPath);
    }
}

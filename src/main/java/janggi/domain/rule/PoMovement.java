package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.PoCollisionDetector;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public class PoMovement implements Movement {

    private static final PoMovement INSTANCE = new PoMovement();

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector;

    private PoMovement() {
        this.routeProvider = StraightRouteProvider.getInstance();
        this.collisionDetector = PoCollisionDetector.getInstance();
    }

    public static PoMovement getInstance() {
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


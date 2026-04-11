package janggi.domain.rule;

import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.DefaultRouteProvider;
import janggi.domain.rule.route.Route;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public class JolMovement implements Movement {

    private static final JolMovement INSTANCE = new JolMovement();

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector = DefaultCollisionDetector.getInstance();

    private JolMovement() {
        List<Route> possibleRoutes = List.of(
                Route.from(List.of(FRONT)),
                Route.from(List.of(LEFT)),
                Route.from(List.of(RIGHT))
        );

        this.routeProvider = new DefaultRouteProvider(possibleRoutes);
    }

    public static JolMovement getInstance() {
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

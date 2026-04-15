package janggi.domain.rule;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;
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
public class MaMovement implements Movement {

    private static final MaMovement INSTANCE = new MaMovement();

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector;

    private MaMovement() {
        List<Route> possibleRoutes = List.of(
                Route.from(List.of(FRONT, FRONT_LEFT)),
                Route.from(List.of(FRONT, FRONT_RIGHT)),
                Route.from(List.of(BACK, BACK_LEFT)),
                Route.from(List.of(BACK, BACK_RIGHT)),
                Route.from(List.of(LEFT, FRONT_LEFT)),
                Route.from(List.of(LEFT, BACK_LEFT)),
                Route.from(List.of(RIGHT, FRONT_RIGHT)),
                Route.from(List.of(RIGHT, BACK_RIGHT))
        );
        this.routeProvider = new DefaultRouteProvider(possibleRoutes);
        this.collisionDetector = DefaultCollisionDetector.getInstance();
    }

    public static MaMovement getInstance() {
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

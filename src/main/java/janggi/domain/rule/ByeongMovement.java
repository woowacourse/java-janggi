package janggi.domain.rule;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.GungSeong;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.DefaultRouteProvider;
import janggi.domain.rule.route.Route;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;
import java.util.Optional;

public class ByeongMovement implements Movement {

    private static final List<Route> POSSIBLE_ROUTES = List.of(
            Route.from(List.of(BACK)),
            Route.from(List.of(LEFT)),
            Route.from(List.of(RIGHT))
    );
    private static final int MAX_DISTANCE = 1;

    private final RouteProvider routeProvider;
    private final GungSeong gungSeong;
    private final CollisionDetector collisionDetector;

    private ByeongMovement(GungSeong gungSeong) {
        this.routeProvider = new DefaultRouteProvider(POSSIBLE_ROUTES);
        this.gungSeong = gungSeong;
        this.collisionDetector = DefaultCollisionDetector.getInstance();
    }

    public static ByeongMovement create(GungSeong gungSeong) {
        return new ByeongMovement(gungSeong);
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        Optional<List<Location>> route = routeProvider.calculateRoute(from, to);
        if (route.isPresent()) {
            return route;
        }

        if (from.calculateVerticalDiff(to) < 0) {
            return Optional.empty();
        }

        return gungSeong.findValidDiagonalPath(from, to)
                .filter(path -> path.size() == MAX_DISTANCE);
    }

    @Override
    public void detectCollision(Side side, List<Piece> piecesOnPath) {
        collisionDetector.check(side, piecesOnPath);
    }
}

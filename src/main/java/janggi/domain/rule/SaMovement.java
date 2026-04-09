package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.GungSeong;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.GungSeongRouteProvider;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;
import java.util.Optional;

public class SaMovement implements Movement {

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector;

    private SaMovement(GungSeong gungSeong) {
        this.routeProvider = GungSeongRouteProvider.of(gungSeong);
        this.collisionDetector = DefaultCollisionDetector.getInstance();
    }

    public static SaMovement create(GungSeong gungSeong) {
        return new SaMovement(gungSeong);
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

package janggi.domain.rule;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.GungSeong;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import java.util.List;
import java.util.Optional;

public class ChaMovement implements Movement {

    private final RouteProvider routeProvider;
    private final GungSeong gungSeong;
    private final CollisionDetector collisionDetector;

    private ChaMovement(GungSeong gungSeong) {
        this.routeProvider = StraightRouteProvider.getInstance();
        this.gungSeong = gungSeong;
        this.collisionDetector = DefaultCollisionDetector.getInstance();
    }

    public static ChaMovement create(GungSeong gungSeong) {
        return new ChaMovement(gungSeong);
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        Optional<List<Location>> route = routeProvider.calculateRoute(from, to);
        if (route.isPresent()) {
            return route;
        }

        return gungSeong.findValidDiagonalPath(from, to);
    }

    @Override
    public void detectCollision(Side side, List<Piece> piecesOnPath) {
        collisionDetector.check(side, piecesOnPath);
    }
}

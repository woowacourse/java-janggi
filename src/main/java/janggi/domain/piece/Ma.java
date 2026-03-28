package janggi.domain.piece;

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
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Route;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;

public class Ma extends Piece {

    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();
    private static final List<Route> POSSIBLE_ROUTES = List.of(
            Route.from(List.of(FRONT, FRONT_LEFT)),
            Route.from(List.of(FRONT, FRONT_RIGHT)),
            Route.from(List.of(RIGHT, FRONT_RIGHT)),
            Route.from(List.of(RIGHT, BACK_RIGHT)),
            Route.from(List.of(LEFT, FRONT_LEFT)),
            Route.from(List.of(LEFT, BACK_LEFT)),
            Route.from(List.of(BACK, BACK_LEFT)),
            Route.from(List.of(BACK, BACK_RIGHT))
    );

    public Ma(Side side) {
        super(PieceType.MA, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return RouteProvider.findValidPath(pieceType, from, to, POSSIBLE_ROUTES);
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}

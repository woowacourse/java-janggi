package janggi.domain.piece;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Route;
import janggi.exception.ErrorCode;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Sang extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.SANG;
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();
    private static final List<Route> moveRoutes = List.of(
            Route.of(List.of(FRONT, FRONT_LEFT, FRONT_LEFT)),
            Route.of(List.of(FRONT, FRONT_RIGHT, FRONT_RIGHT)),
            Route.of(List.of(RIGHT, FRONT_RIGHT, FRONT_RIGHT)),
            Route.of(List.of(RIGHT, BACK_RIGHT, BACK_RIGHT)),
            Route.of(List.of(LEFT, FRONT_LEFT, FRONT_LEFT)),
            Route.of(List.of(LEFT, BACK_LEFT, BACK_LEFT)),
            Route.of(List.of(BACK, BACK_LEFT, BACK_LEFT)),
            Route.of(List.of(BACK, BACK_RIGHT, BACK_RIGHT))
    );

    public Sang(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    protected List<Location> resolveRoute(Intersection from, Intersection to) {
        for (Route route : moveRoutes) {
            List<Location> locations = route.apply(from.getLocation());
            if (locations.getLast().equals(to.getLocation())) {
                return locations;
            }
        }

        throw new RouteResolveException(ErrorCode.ROUTE_RESOLVE_ERROR);
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}

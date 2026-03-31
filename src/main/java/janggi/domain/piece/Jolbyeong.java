package janggi.domain.piece;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.Route;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Jolbyeong extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.JOLBYEOUNG;
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();
    private final List<Route> moveRoutes = List.of(
            Route.of(List.of(getRealFront(side))),
            Route.of(List.of(LEFT)),
            Route.of(List.of(RIGHT))
    );

    public Jolbyeong(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        for (Route route : moveRoutes) {
            List<Location> locations = route.apply(from);
            if (locations.getLast().equals(to)) {
                return locations;
            }
        }
        throw new RouteResolveException(pieceType, from, to);
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }

    private Direction getRealFront(Side side) {
        if (side.equals(Side.HAN)) {
            return FRONT;
        }
        return BACK;
    }
}

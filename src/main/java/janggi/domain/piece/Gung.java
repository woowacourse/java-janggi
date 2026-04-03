package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.GungSeongRouteProvider;
import janggi.domain.rule.route.RouteProvider;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Gung extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.GUNG;
    private static final RouteProvider ROUTE_PROVIDER = GungSeongRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Gung(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        try {
            return ROUTE_PROVIDER.calculateRoute(from, to);
        } catch (RouteResolveException e) {
            throw new RouteResolveException(pieceType, from, to);
        }
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}

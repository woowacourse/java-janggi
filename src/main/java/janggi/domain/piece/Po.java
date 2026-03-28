package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.PoCollisionDetector;
import janggi.domain.rule.route.GungSeongRouteProvider;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;

public class Po extends ActivePiece {

    private static final String PIECE_NAME = "포";
    private static final RouteProvider ROUTE_PROVIDER = new GungSeongRouteProvider();
    private static final CollisionDetector COLLISION_DETECTOR = new PoCollisionDetector();

    public Po(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        try {
            return ROUTE_PROVIDER.calculateRoute(from, to);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("포는 해당 위치에 도달할 수 없습니다.");
        }
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}


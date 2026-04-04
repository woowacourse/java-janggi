package janggi.domain.piece;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import janggi.exception.ErrorCode;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Jolbyeong extends ActivePiece {

    private static final int MAX_MOVE_DISTANCE = 1;
    private static final PieceType PIECE_TYPE = PieceType.JOLBYEOUNG;

    private static final RouteProvider ROUTE_PROVIDER = StraightRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Jolbyeong(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    public List<Location> calculateRoute(Intersection from, Intersection to) {
        List<Location> moveRoutes;

        try {
            moveRoutes = ROUTE_PROVIDER.calculateRoute(from, to);
            validateMoveBack(from.getLocation(), to.getLocation());

            if (moveRoutes.size() > MAX_MOVE_DISTANCE) {
                throw new RouteResolveException(pieceType, from.getLocation(), to.getLocation());
            }
        } catch (RouteResolveException e) {
            throw new RouteResolveException(pieceType, from.getLocation(), to.getLocation());
        }

        return moveRoutes;
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }

    private void validateMoveBack(Location from , Location to) {
        Direction sideRealBack = side.getBackDirection();
        Direction locationDirection = Direction.getDirection(from, to);

        if(sideRealBack.isSameDirection(locationDirection)) {
            throw new RouteResolveException(ErrorCode.JOLBYEONG_MOVE_BACK_ERROR);
        }
    }
}

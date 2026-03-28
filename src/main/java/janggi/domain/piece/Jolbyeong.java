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
import java.util.List;

public class Jolbyeong extends ActivePiece {

    private static final String PIECE_NAME = "졸병";
    private static final String CHO_PIECE_NAME = "졸";
    private static final String HAN_PIECE_NAME = "병";
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Jolbyeong(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(getRealFront(side))),
                Route.of(List.of(LEFT)),
                Route.of(List.of(RIGHT))
        );

        for (Route route : directions) {
            List<Location> locations = route.apply(from);
            if (locations.getLast().equals(to)) {
                return locations;
            }
        }
        throw new IllegalArgumentException(getName() + "은 해당 위치에 도달할 수 없습니다.");
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

    @Override
    public String getName() {
        if (side.equals(Side.HAN)) {
            return HAN_PIECE_NAME;
        }
        return CHO_PIECE_NAME;
    }
}

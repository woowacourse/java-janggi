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
import java.util.List;

public class Sang extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.SA;
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Sang(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(FRONT, FRONT_LEFT, FRONT_LEFT)),
                Route.of(List.of(FRONT, FRONT_RIGHT, FRONT_RIGHT)),
                Route.of(List.of(RIGHT, FRONT_RIGHT, FRONT_RIGHT)),
                Route.of(List.of(RIGHT, BACK_RIGHT, BACK_RIGHT)),
                Route.of(List.of(LEFT, FRONT_LEFT, FRONT_LEFT)),
                Route.of(List.of(LEFT, BACK_LEFT, BACK_LEFT)),
                Route.of(List.of(BACK, BACK_LEFT, BACK_LEFT)),
                Route.of(List.of(BACK, BACK_RIGHT, BACK_RIGHT))
        );

        for (Route route : directions) {
            List<Location> locations = route.apply(from);
            if (locations.getLast().equals(to)) {
                return locations;
            }
        }

        throw new IllegalArgumentException("상은 해당 위치에 도달할 수 없습니다.");
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}

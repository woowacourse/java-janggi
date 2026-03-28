package janggi.domain.piece;

import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.Route;
import java.util.List;

public class Jolbyeong extends Piece {

    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Jolbyeong(Side side) {
        super(determineType(side), side);
    }

    private static PieceType determineType(Side side) {
        if (side == Side.HAN) {
            return PieceType.BYEONG;
        }
        if (side == Side.CHO) {
            return PieceType.JOL;
        }
        throw new IllegalArgumentException("진영이 존재하지 않아 기물명을 정할 수 없습니다.");
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(getFrontDirection())),
                Route.of(List.of(LEFT)),
                Route.of(List.of(RIGHT))
        );

        for (Route route : directions) {
            List<Location> locations = route.apply(from);
            if (locations.getLast().equals(to)) {
                return locations;
            }
        }
        throw new IllegalArgumentException(getPieceType().getNameFormat() + "은 해당 위치에 도달할 수 없습니다.");
    }

    private Direction getFrontDirection() {
        if (side == Side.HAN) {
            return Direction.FRONT;
        }
        if (side == Side.CHO) {
            return Direction.BACK;
        }
        throw new IllegalStateException("진영이 존재하지 않아 전진 방향을 정할 수 없습니다.");
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}

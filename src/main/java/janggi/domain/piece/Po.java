package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.direction.GungSeongRouteRule;
import janggi.domain.direction.RouteRule;
import java.util.List;

public class Po extends Piece {

    private static final String PIECE_NAME = "포";
    private static final RouteRule ROUTE_RULE = new GungSeongRouteRule();

    public Po(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        try {
            return ROUTE_RULE.calculateRoute(from, to);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("포는 해당 위치에 도달할 수 없습니다.");
        }
    }
}


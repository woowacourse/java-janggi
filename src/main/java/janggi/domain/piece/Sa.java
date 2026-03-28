package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.GungSeongRouteProvider;
import java.util.List;

public class Sa extends Piece {

    private static final String PIECE_NAME = "사";
    private static final RouteProvider ROUTE_PROVIDER = new GungSeongRouteProvider();

    public Sa(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        try {
            return ROUTE_PROVIDER.calculateRoute(from, to);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("사는 해당 위치에 도달할 수 없습니다.");
        }
    }
}

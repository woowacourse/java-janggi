package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.direction.RouteProvider;
import janggi.domain.direction.StraightRouteProvider;
import java.util.List;

public class Cha extends Piece {

    private static final String PIECE_NAME = "차";
    private static final RouteProvider ROUTE_PROVIDER = new StraightRouteProvider();

    public Cha(Side side) {
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
            throw new IllegalArgumentException("차는 해당 위치에 도달할 수 없습니다.");
        }
    }
}

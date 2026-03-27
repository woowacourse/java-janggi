package janggi.domain.piece;

import static janggi.domain.direction.Direction.*;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.direction.Route;
import java.util.List;

public class Ma extends Piece {

    private static final String PIECE_NAME = "마";

    public Ma(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(FRONT, FRONT_LEFT)),
                Route.of(List.of(FRONT, FRONT_RIGHT)),
                Route.of(List.of(RIGHT, FRONT_RIGHT)),
                Route.of(List.of(RIGHT, BACK_RIGHT)),
                Route.of(List.of(LEFT, FRONT_LEFT)),
                Route.of(List.of(LEFT, BACK_LEFT)),
                Route.of(List.of(BACK, BACK_LEFT)),
                Route.of(List.of(BACK, BACK_RIGHT))
        );

        for(Route route : directions) {
            List<Location> locations = route.apply(from);
            if(locations.getLast().equals(to)) {
                return locations;
            }
        }

        throw new IllegalArgumentException("마는 해당 위치에 도달할 수 없습니다.");
    }
}

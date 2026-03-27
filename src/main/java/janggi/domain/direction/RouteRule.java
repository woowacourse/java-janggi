package janggi.domain.direction;

import janggi.domain.Location;
import java.util.List;

public interface RouteRule {

    List<Location> calculateRoute(Location from, Location to);
}

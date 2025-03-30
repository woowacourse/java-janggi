package janggi.position;

import java.util.Map;

public interface BoardRouteGenerator {

    Map<Position, Directions> generate();
}

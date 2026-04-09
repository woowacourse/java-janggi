package domain.movement.strategy;

import domain.board.Intersection;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.Collection;
import java.util.List;

public interface MoveStrategy {

    List<Route> getRoutes(
            Intersection from,
            Collection<Vector> vectors
    );

    List<Route> getCardinalRoutes(Intersection from);

    List<Route> getPalaceRoutes(Intersection from);

    List<Route> getPalaceRoutes(
            Intersection from,
            Collection<Vector> allowedPalaceVectors
    );
}

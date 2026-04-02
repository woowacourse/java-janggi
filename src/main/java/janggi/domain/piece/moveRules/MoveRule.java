package janggi.domain.piece.moveRules;

import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.List;

public interface MoveRule {
    List<Route> findRoutes(Team team);
}

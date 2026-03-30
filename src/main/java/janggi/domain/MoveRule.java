package janggi.domain;

import java.util.List;

public interface MoveRule {
    List<Route> findRoutes(Team team);
}

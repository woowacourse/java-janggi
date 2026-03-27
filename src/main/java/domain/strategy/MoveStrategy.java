package domain.strategy;

import domain.Team;

import java.util.List;

public interface MoveStrategy {
    List<Direction> getDirections(Team team);
}

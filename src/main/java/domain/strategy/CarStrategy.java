package domain.strategy;

import domain.Team;

import java.util.ArrayList;
import java.util.List;

public class CarStrategy implements MoveStrategy {
    @Override
    public List<Direction> getDirections(Team team) {
        return new ArrayList<>(List.of(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH));
    }
}

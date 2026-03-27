package domain.strategy;

import domain.Team;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {
    @Override
    public List<Direction> getDirections(Team team) {
        List<Direction> directions = new ArrayList<>(List.of(Direction.EAST, Direction.WEST));

        if (team == Team.CHO) {
            directions.add(Direction.NORTH);
        }
        if (team == Team.HAN) {
            directions.add(Direction.SOUTH);
        }
        return directions;
    }
}

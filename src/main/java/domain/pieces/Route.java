package domain.pieces;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class Route {
    public static List<Position> path(Position position, List<Direction> directions) {
        List<Position> path = new ArrayList<>();
        for (Direction direction : directions) {
            if(position.canMove(direction)) {
                position = position.move(direction);
                path.add(position);
            }
        }
        
        return path;
    }
}

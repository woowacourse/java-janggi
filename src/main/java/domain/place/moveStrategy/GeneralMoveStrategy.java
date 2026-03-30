package domain.place.moveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public List<Position> getPath(Position from) {
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .toList();
    }

    @Override
    public boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide) {
        Place toPlace = board.getOrDefault(to, new Empty());
        if(toPlace.hasSide(fromSide)){
            return false;
        }
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .anyMatch(to::equals);
    }

}

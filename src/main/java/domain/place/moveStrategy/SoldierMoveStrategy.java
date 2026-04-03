package domain.place.moveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    private final List<Direction> directions;

    public SoldierMoveStrategy(Side side) {
        this.directions = initDirections(side);
    }

    private List<Direction> initDirections(Side side) {
        if (side == Side.CHO) {
            return List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        }
        return List.of(Direction.TOP, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public List<Position> getPath(Position from) {
        return directions.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .toList();
    }

    @Override
    public boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide) {

        Place toPlace = board.getOrDefault(to, new Empty());
        if (toPlace.hasSide(fromSide)) {
            return false;
        }
        return directions.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .anyMatch(to::equals);
    }

}

package domain.place.palaceMoveStrategy;

import domain.place.Empty;
import domain.place.PalaceArea;
import domain.place.Place;
import domain.place.moveStrategy.Direction;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PalaceSoldierMoveStrategy implements PalaceMoveStrategy {

    private final List<Direction> directions;

    public PalaceSoldierMoveStrategy(Side side) {
        this.directions = initDirections(side);
    }

    private List<Direction> initDirections(Side side) {
        if (side == Side.CHO) {
            return List.of(Direction.LEFT_DOWN, Direction.RIGHT_DOWN);
        }
        return List.of(Direction.RIGHT_TOP, Direction.LEFT_TOP);
    }

    @Override
    public List<Position> getPath(Position from) {
        return directions.stream()
                .flatMap(direction -> from.moveIfInBounds(direction).stream())
                .filter(PalaceArea::isInsidePalace)
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
                .filter(PalaceArea::isInsidePalace)
                .anyMatch(to::equals);
    }

}

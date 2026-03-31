package domain.place.palaceMoveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.moveStrategy.Direction;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PalaceStraightMoveStrategy implements PalaceMoveStrategy {
    private static final List<Direction> DIAGONAL_DIRECTIONS = List.of(
            Direction.RIGHT_TOP,
            Direction.LEFT_TOP,
            Direction.LEFT_DOWN,
            Direction.RIGHT_DOWN
    );

    @Override
    public List<Position> getPath(Position from) {
        List<Position> result = new ArrayList<>();
        DIAGONAL_DIRECTIONS.forEach(direction -> collectLinePositions(result, from, direction));
        return result;
    }

    private void collectLinePositions(List<Position> result, Position from, Direction direction) {
        Optional<Position> current = from.moveIfInBounds(direction);

        while (current.isPresent() && PalaceMovementRule.isInsidePalace(current.get())) {
            Position pos = current.get();
            result.add(pos);

            current = pos.moveIfInBounds(direction);
        }
    }

    @Override
    public boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide) {
        Place toPlace = board.getOrDefault(to, new Empty());
        if (toPlace.hasSide(fromSide)) {
            return false;
        }
        return DIAGONAL_DIRECTIONS.stream()
                .anyMatch(direction -> isValidStraight(board, from, to, direction));
    }

    private boolean isValidStraight(Map<Position, Place> board,
                                    Position from,
                                    Position to,
                                    Direction direction) {
        Optional<Position> current = from.moveIfInBounds(direction);
        while (current.isPresent() && !to.equals(current.get())) {
            Position pos = current.get();
            Place place = board.getOrDefault(pos, new Empty());
            if (!place.isEmpty()) {
                return false;
            }

            current = current.get().moveIfInBounds(direction);
        }

        return current.filter(to::equals).isPresent();
    }

}

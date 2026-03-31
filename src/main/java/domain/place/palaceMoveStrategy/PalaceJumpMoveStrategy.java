package domain.place.palaceMoveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.moveStrategy.Direction;
import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PalaceJumpMoveStrategy implements PalaceMoveStrategy {

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
        if (isTargetCannon(getPlace(board, to))) {
            return false;
        }

        Place toPlace = getPlace(board, to);
        if (toPlace.hasSide(fromSide)) {
            return false;
        }
        return DIAGONAL_DIRECTIONS.stream()
                .anyMatch(direction -> isValidJump(board, from, to, direction));
    }

    private boolean isValidJump(Map<Position, Place> board,
                                Position from,
                                Position to,
                                Direction direction) {
        Optional<Position> current = from.moveIfInBounds(direction);
        if (current.isEmpty() || !PalaceMovementRule.isInsideSpecialPalace(current.get())) {
            return false;
        }
        Place middlePlace = getPlace(board, current.get());
        current = current.get().moveIfInBounds(direction);

        return !isTargetCannon(middlePlace) && !middlePlace.isEmpty()
                && current.filter(to::equals).isPresent();
    }

    private Place getPlace(Map<Position, Place> board, Position position) {
        return board.getOrDefault(position, new Empty());
    }

    private boolean isTargetCannon(Place place) {
        return place.isSameSymbol(PieceSymbol.CANNON);
    }

}

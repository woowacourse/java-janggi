package domain.piece.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public final class ChariotMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Intersection> candidateIntersections(Intersection from, Side side) {
        return allDirectionIntersections(from, side);
    }

    private List<Intersection> allDirectionIntersections(Intersection from, Side side) {
        return side.getAllDirections()
                .stream()
                .map(direction -> allIntersectionsOfDirection(from, direction))
                .flatMap(List::stream)
                .toList();
    }

    private List<Intersection> allIntersectionsOfDirection(Intersection from, Direction direction) {
        List<Intersection> toDirectionIntersections = new ArrayList<>();

        Intersection current = direction.moveForward(from, MOVE_AMOUNT);
        while (current.isInBounds()) {
            toDirectionIntersections.add(current);
            current = direction.moveForward(current, MOVE_AMOUNT);
        }

        return List.copyOf(toDirectionIntersections);
    }
}

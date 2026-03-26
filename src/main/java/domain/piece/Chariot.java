package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;

public class Chariot extends StaticPositionedPiece {

    private static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(0);
    private static final List<Integer> INITAL_FILES = List.of(1, 9);

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        Direction forwardDirection = side.getForwardDirection();

        return INITAL_FILES.stream()
                .map(this::currentIntersection)
                .map(intersection -> forwardDirection.moveForward(intersection, FAR_FROM_BASE_ROW))
                .toList();
    }

    private Intersection currentIntersection(int file) {
        return new Intersection(side.getBaseRow(), file);
    }
}

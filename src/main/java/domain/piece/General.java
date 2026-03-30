package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;

public class General extends PalacePiece {

    private static final int INITIAL_FILE = 5;
    private static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(1);

    public General(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        Direction forwardDirection = side.getForwardDirection();

        return List.of(forwardDirection.moveForward(
                new Intersection(side.getBaseRow(), INITIAL_FILE),
                FAR_FROM_BASE_ROW
        ));
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }
}

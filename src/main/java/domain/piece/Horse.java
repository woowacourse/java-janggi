package domain.piece;

import domain.position.Direction;
import domain.position.MoveDirection;
import java.util.List;

public class Horse extends MultiStepPiece {

    private static final int REQUIRED_PATH_SIZE = 2;

    public Horse(Camp camp) {
        super(camp, PieceType.HORSE);
    }

    @Override
    protected List<List<Direction>> getMoveDirections() {
        return MoveDirection.ofHorse();
    }

    @Override
    protected int getRequiredPathSize() {
        return REQUIRED_PATH_SIZE;
    }
}

package domain.piece;

import domain.position.Direction;
import domain.position.MoveDirection;
import java.util.List;

public class Elephant extends MultiStepPiece {

    private static final int REQUIRED_PATH_SIZE = 3;

    public Elephant(Camp camp) {
        super(camp, PieceType.ELEPHANT);
    }

    @Override
    protected List<List<Direction>> getMoveDirections() {
        return MoveDirection.ofElephant();
    }

    @Override
    protected int getRequiredPathSize() {
        return REQUIRED_PATH_SIZE;
    }
}

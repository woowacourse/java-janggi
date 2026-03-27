package domain.strategy;

import domain.Position;
import java.util.List;

public class NoneMoveableStrategy extends MoveStrategy {

    private NoneMoveableStrategy(Position position) {
        super(position);
    }

    public static NoneMoveableStrategy of(Position position) {
        return new NoneMoveableStrategy(position);
    }

    @Override
    public void updateRoute() {
    }

    @Override
    public boolean isMoveAble(Position position) {
        return false;
    }

    @Override
    public boolean isInvalidPath(Position destination, List<Position> piecePositions) {
        return false;
    }
}

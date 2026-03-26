package domain.strategy;

import domain.Position;
import java.util.List;

public class NoneMoveableStrategy extends MoveStrategy {

    public NoneMoveableStrategy(Position position) {
        super(position);
    }

    @Override
    public boolean isMoveAble(Position position) {
        return false;
    }

    @Override
    public boolean isValidPath(Position destination, List<Position> piecePositions) {
        return false;
    }
}

package domain.strategy;

import domain.Position;
import java.util.List;

public abstract class MoveStrategy {

    protected Position position;

    MoveStrategy(Position position) {
        this.position = position;
    }

    public abstract void updateRoute();

    public abstract boolean isMoveAble(Position position);

    public abstract boolean isInvalidPath(Position destination, List<Position> piecePositions);

    public void changePosition(Position movedPosition) {
        this.position = movedPosition;
    }
    public Position position() {
        return position;
    }
}

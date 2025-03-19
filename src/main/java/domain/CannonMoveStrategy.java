package domain;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.minusX(destination);
        int differenceY = current.minusY(destination);
        return differenceX == 0 || differenceY == 0;
    }
}

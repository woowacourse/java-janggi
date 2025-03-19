package domain;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return differenceX == 0 || differenceY == 0;
    }
}

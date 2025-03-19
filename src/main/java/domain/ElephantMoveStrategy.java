package domain;

public class ElephantMoveStrategy implements MoveStrategy{

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return (differenceX == 2 && differenceY == 3) || (differenceX == 3 && differenceY == 2);
    }
}

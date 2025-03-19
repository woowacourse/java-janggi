package domain;

public class ChoPawnMoveStrategy implements MoveStrategy{

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        boolean a = differenceX == 0 || differenceY == 0;
        boolean b = differenceX == 1 || differenceY == 1;
        boolean c = destination.isUp(current);
        return a && b && c;
    }
}

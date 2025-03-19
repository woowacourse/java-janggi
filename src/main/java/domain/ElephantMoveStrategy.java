package domain;

public class ElephantMoveStrategy implements MoveStrategy{

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.minusX(destination);
        int differenceY = current.minusY(destination);
        return (differenceX == 2 && differenceY == 3) || (differenceX == 3 && differenceY == 2);
    }
}

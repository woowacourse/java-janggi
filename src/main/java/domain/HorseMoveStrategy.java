package domain;

public class HorseMoveStrategy implements MoveStrategy{

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.minusX(destination);
        int differenceY = current.minusY(destination);
        return (differenceX == 1 && differenceY == 2) || (differenceX == 2 && differenceY == 1);
    }
}

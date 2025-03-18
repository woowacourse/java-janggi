package model;

public class AbstractMove implements Move{

    @Override
    public void up() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void down() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void diagonalUpLeft() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalUpRight() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalDownLeft() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalDownRight() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }
}

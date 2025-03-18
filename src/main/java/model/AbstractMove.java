package model;

public class AbstractMove implements Move{

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void diagonalUpLeft() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void diagonalUpRight() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void diagonalDownLeft() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void diagonalDownRight() {
        throw new UnsupportedOperationException();
    }
}

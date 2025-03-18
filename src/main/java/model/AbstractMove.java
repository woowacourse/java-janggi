package model;

public class AbstractMove implements Move{

    @Override
    public void up(int moveAmount) {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void down(int moveAmount) {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void left(int moveAmount) {

    }

    @Override
    public void right(int moveAmount) {

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
    public void diagonalLeftUp() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalLeftDown() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalRightUp() {
        throw new UnsupportedOperationException("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @Override
    public void diagonalRightDown() {
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

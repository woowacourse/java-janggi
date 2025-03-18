package model;

public interface Move {

    void up(int moveAmount);
    void down(int moveAmount);
    void left(int moveAmount);
    void right(int moveAmount);
    void diagonalUpLeft();
    void diagonalUpRight();
    void diagonalLeftUp();
    void diagonalLeftDown();
    void diagonalRightUp();
    void diagonalRightDown();
    void diagonalDownLeft();
    void diagonalDownRight();
}

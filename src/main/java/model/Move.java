package model;

public interface Move {

    void up(int moveAmount);
    void down(int moveAmount);
    void left(int moveAmount);
    void right(int moveAmount);
    void diagonalUpLeft();
    void diagonalUpRight();
    void diagonalDownLeft();
    void diagonalDownRight();
}

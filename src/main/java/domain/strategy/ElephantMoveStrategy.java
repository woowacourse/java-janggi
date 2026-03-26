package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        // row차이 :     3      -3      2       -2
        // col차이 :   2 -2    2 -2    3 -3    3 -3

        if (isNotCorrectPath(from, to))
            return false;

        int nx = 0, ny = 0;
        if (from.getRow() - to.getRow() == 3) { // 아래
            if (from.getCol() - to.getCol() == 2) { // 왼쪽
                nx = -3;
                ny = -2;
            }
            if (from.getCol() - to.getCol() == -2) { // 오른쪽
                nx = -3;
                ny = 2;
            }
        }
        if (from.getRow() - to.getRow() == -3) { // 위
            if (from.getCol() - to.getCol() == 2) { // 왼쪽
                nx = 3;
                ny = -2;
            }
            if (from.getCol() - to.getCol() == -2) { // 오른쪽
                nx = 3;
                ny = 2;
            }
        }
        if (from.getRow() - to.getRow() == 2) {
            if (from.getCol() - to.getCol() == 3) { // 왼쪽 아래대각 아래대각
                nx = -2;
                ny = -3;
            }
            if (from.getCol() - to.getCol() == -3) { // 오른쪽 아래대각 아래대각
                nx = -2;
                ny = 3;
            }
        }
        if (from.getRow() - to.getRow() == -2) {
            if (from.getCol() - to.getCol() == 3) { // 왼쪽 위대각 위대각
                nx = 2;
                ny = -3;
            }
            if (from.getCol() - to.getCol() == -3) { // 오른쪽 위대각 위대각
                nx = 2;
                ny = 3;
            }
        }

        int row = from.getRow();
        int col = from.getCol();
//        if (board.isExistPosition(직전)){
//            return false;
//        }
//        if (board.isExistPosition(첫번쨰 대각)) {
//            return false;
//        }


        return true;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        if (Math.abs(from.getRow() - to.getRow()) == 2) {
            if (Math.abs(from.getCol() - to.getCol()) != 3) {
                return true;
            }
        }
        if (Math.abs(from.getRow() - to.getRow()) == 3) {
            if (Math.abs(from.getCol() - to.getCol()) != 2) {
                return true;
            }
        }
        return false;
    }
}

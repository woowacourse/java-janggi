package janggi.domain.mouveRule;

import janggi.domain.Board;
import janggi.domain.vo.Position;

public class HorseMoveRule implements MoveRule {

    @Override
    public void move(Position from, Position to, Board board) {

        // 이동하는곳 확인

        int[][] pattern = new int[4][6];

        pattern[0] = new int[]{-1, 0, -2, -1, -2, 1};
        pattern[1] = new int[]{0, 1, -1, 2, 1, 2};
        pattern[2] = new int[]{1, 0, 2, -1, 2, 1};
        pattern[3] = new int[]{0, -1, -1, -1, 1, -1};

        boolean flag = false;

        for (int[] x : pattern) {
            int fromRow = from.getRow();
            int fromCol = from.getCol();

            if ((to.getRow() == (fromRow + x[2]) && to.getCol() == (fromCol + x[3]))
                    || (to.getRow() == (fromRow + x[4]) && to.getCol() == (fromCol + x[5]))) {
                flag = true;

                Position blockPosition = new Position(x[0], x[1]);
                if (!board.isEmptyPosition(blockPosition)) {
                    throw new IllegalArgumentException("[ERROR] 이동경로에 기물이 있습니다.");
                }
            }
        }

        if (!flag) {
            throw new IllegalArgumentException("[ERROR] 마는 직선이동 후 대각선으로만 이동가능합니다.");

        }


    }

}

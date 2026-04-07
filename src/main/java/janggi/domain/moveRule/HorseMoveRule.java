package janggi.domain.moveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;
import java.util.List;

public class HorseMoveRule implements MoveRule {

    private static final List<int[]> MOVE_PATTERNS = List.of(
            //이동 가능, 막힘 -> 행보다 열이 작은게 더 직관적으로 이해가 편함
            new int[]{2, 1, 1, 0},  // 아래2 오른1 , 막힘(1,0)
            new int[]{2, -1, 1, 0},  // 아래2 왼1  , 막힘(1,0)
            new int[]{-2, 1, -1, 0},  // 위2 오른1 , 막힘(-1,0)
            new int[]{-2, -1, -1, 0},  // 위2 왼1  , 막힘(-1,0)
            new int[]{1, 2, 0, 1},  // 아래1 오른2 , 막힘(0,1)
            new int[]{1, -2, 0, -1},  // 아래1 왼2 , 막힘(0,-1)
            new int[]{-1, 2, 0, 1},  // 위1 오른2 , 막힘(0,1)
            new int[]{-1, -2, 0, -1}   // 위1 왼2 ,  막힘(0,-1)
    );

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        for (int[] pattern : MOVE_PATTERNS) {
            if (matchesPattern(from, to, pattern) && isNotBlocked(from, pattern, board)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesPattern(Position from, Position to, int[] pattern) {
        return to.getRow() == from.getRow() + pattern[0]
                && to.getCol() == from.getCol() + pattern[1];
    }

    private boolean isNotBlocked(Position from, int[] pattern, BoardView board) {
        int blockRow = from.getRow() + pattern[2];
        int blockCol = from.getCol() + pattern[3];
        return isInBounds(blockRow, blockCol)
                && board.isEmptyPosition(new Position(blockRow, blockCol));
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 9 && col >= 0 && col <= 8;
    }
}

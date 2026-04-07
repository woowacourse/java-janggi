package janggi.domain.moveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;
import java.util.List;

public class ElephantMoveRule implements MoveRule {

    private static final List<int[]> MOVE_PATTERNS = List.of(
            // 이동가능, 막힘1, 막힘2
            new int[]{2, 3, 1, 0, 2, 1},  // 아래2 오른3, 막힘1(1,0) 막힘2(2,1)
            new int[]{2, -3, 1, 0, 2, -1},  // 아래2 왼3, 막힘1(1,0) 막힘2(2,-1)
            new int[]{-2, 3, -1, 0, -2, 1},  // 위2 오른3, 막힘1(-1,0) 막힘2(-2,1)
            new int[]{-2, -3, -1, 0, -2, -1},  // 위2 왼3, 막힘1(-1,0) 막힘2(-2,-1)
            new int[]{3, 2, 0, 1, 1, 2},  // 아래3 오른2, 막힘1(0,1) 막힘2(1,2)
            new int[]{3, -2, 0, -1, 1, -2},  // 아래3 왼2, 막힘1(0,-1) 막힘2(1,-2)
            new int[]{-3, 2, 0, 1, -1, 2},  // 위3 오른2, 막힘1(0,1) 막힘2(-1,2)
            new int[]{-3, -2, 0, -1, -1, -2}   // 위3 왼2, 막힘1(0,-1) 막힘2(-1,-2)
    );

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        for (int[] pattern : MOVE_PATTERNS) {
            if (matchesPattern(from, to, pattern) && isNotBlockedPath(from, pattern, board)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesPattern(Position from, Position to, int[] pattern) {
        return to.getRow() == from.getRow() + pattern[0]
                && to.getCol() == from.getCol() + pattern[1];
    }

    private boolean isNotBlockedPath(Position from, int[] pattern, BoardView board) {
        return isBlockClear(from.getRow() + pattern[2], from.getCol() + pattern[3], board)
                && isBlockClear(from.getRow() + pattern[4], from.getCol() + pattern[5], board);
    }

    private boolean isBlockClear(int row, int col, BoardView board) {
        return isInBounds(row, col) && board.isEmptyPosition(new Position(row, col));
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row <= 9 && col >= 0 && col <= 8;
    }
}
